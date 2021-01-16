package com.mesutemre.kullanici.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mesutemre.enums.RoleTurEnum;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.kullanici.rowmapper.KullaniciRowMapper;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.parametre.service.ParametreService;
import com.mesutemre.rol.model.RolModel;
import com.mesutemre.rol.service.RolService;

@Repository
public class KullaniciDao implements IKullaniciDao {

	private JdbcTemplate jdbctemplate;
	
	@Autowired
	private ParametreService parametreService;
	
	@Autowired
	private RolService		rolService;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}

	@Override
//	@Cacheable(value="kullanicicache",key="#username",unless="#result==null")
	public KullaniciModel getKullaniciBilgi(String username) {
		StringBuilder sql = new StringBuilder();
		KullaniciModel kullanici = null;
		
		sql.append(" SELECT 								");
		sql.append(" username,								");
		sql.append(" ad,soyad,dogumtarihi,eposta,cinsiyet, 	");
		sql.append(" resim,haberdarmi,ilgialanlari,enabled 	");
		sql.append(" FROM 									");
		sql.append(" kutuphanesistemi.users 				");
		sql.append(" WHERE username=? 						");
		
		List<KullaniciModel> liste = jdbctemplate.query(sql.toString(),
									new Object[] {username},
									new KullaniciRowMapper());
		if(liste != null && liste.size()>0){
			kullanici = liste.get(0);
			kullanici.setIlgiAlanlari(parametreService.getKullaniciIlgiAlanlari(username));
		}
		
		return kullanici;
	}
	
	@Override
	public ErrorDetail kullaniciKaydet(KullaniciModel model) {
		StringBuilder sql = new StringBuilder();
		ErrorDetail errorDetail = null;
		
		sql.append(" INSERT INTO kutuphanesistemi.users ");
		sql.append(" ( ");
		sql.append(" username,password,ad,soyad,dogumtarihi, ");
		sql.append(" eposta,cinsiyet,haberdarmi,enabled ");
		sql.append(" )  ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" ?,?,?,?,?,?,?,?,true ");
		sql.append(" ) ");
		
		int result = jdbctemplate.update(sql.toString(),
				new Object[]{
			model.getUsername(),
			model.getPassword(),
			model.getAd(),
			model.getSoyad(),
			model.getDogumTarihi(),
			model.getEposta(),
			model.getCinsiyet().getValue(),
			String.valueOf(model.isHaberdarmi())
		});
		
		RolModel rolModel = new RolModel();
		
		rolModel.setRolKullanici(model);
		rolModel.setRol(RoleTurEnum.NORMAL_USER);
		rolModel.setOlusturan(model);
		
		errorDetail = rolService.insertRol(rolModel);
		errorDetail = parametreService.saveKullaniciIlgiAlanlar(model, model.getIlgiAlanlari());
		
		return errorDetail;
	}
	
	@Override
	public List<KullaniciModel> kullaniciFiltrele(String username) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT 								");
		sql.append(" username,								");
		sql.append(" ad,soyad,dogumtarihi,eposta,cinsiyet, 	");
		sql.append(" resim,haberdarmi,ilgialanlari,enabled 	");
		sql.append(" FROM 									");
		sql.append(" kutuphanesistemi.users 				");
		sql.append(" WHERE username=? 						");
		
		List<KullaniciModel> liste = jdbctemplate.query(sql.toString(),
									new Object[] {username},
									new KullaniciRowMapper());
		
		return liste;
	}
	
	@Override
	public ErrorDetail kullaniciGuncelle(KullaniciModel model) {
		ErrorDetail errorDetail = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE kutuphanesistemi.users ");
		sql.append(" SET ");
		sql.append(" ad=?,soyad=?,dogumtarihi=?,cinsiyet=?, ");
		sql.append(" haberdarmi=?,eposta=? ");
		sql.append(" WHERE ");
		sql.append(" username=? ");
		
		int result = jdbctemplate.update(sql.toString()
				,new Object[]{
					model.getAd(),
					model.getSoyad(),
					model.getDogumTarihi(),
					model.getCinsiyet().getValue(),
					String.valueOf(model.isHaberdarmi()),
					model.getEposta(),
					model.getUsername()
		});
		
		if(result == 0){
			errorDetail = new ErrorDetail();
		}
		
		return errorDetail;
	}
	
	@Override
	public void kullaniciResimGuncelle(String username) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE kutuphanesistemi.users ");
		sql.append(" SET resim=? ");
		sql.append(" WHERE ");
		sql.append(" username=? ");
		
		jdbctemplate.update(sql.toString()
				,new Object[]{
					username+".jpg",
					username
		});
	}
}
