package com.mesutemre.parametre.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mesutemre.enums.ParametreDurumEnum;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;
import com.mesutemre.parametre.model.YayineviModel;
import com.mesutemre.parametre.rowmapper.IlgiAlanlariRowMapper;
import com.mesutemre.parametre.rowmapper.YayinEviModelRowMapper;

/**
 * @author mesutemre.celenk
 *
 */
@Repository
public class ParametreDao implements IParametreDao{
	
	private NamedParameterJdbcTemplate jdbctemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbctemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
//	@Cacheable(value="kitapturleri",key="#kriter",unless="#result==null")
	public List<IlgiAlanlariParametreModel> ilgiAlanListe(IlgiAlanlariParametreModel kriter) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" SELECT ");
		sql.append(" i.id,i.aciklama,i.olusturan,i.olusturma_tar,i.status,i.resim ");
		sql.append(" FROM ");
		sql.append(" kutuphanesistemi.ilgi_alanlari i ");
		sql.append(" WHERE i.status='AKTIF' ");
		
		
		if(kriter != null && kriter.getOlusturan() != null){
			sql.append(" AND ");
			sql.append(" i.olusturan =:olusturan ");
			params.addValue("olusturan", kriter.getOlusturan().getUsername());
		}
		
		if(kriter != null && kriter.getId() != 0){
			sql.append(" AND ");
			sql.append(" i.id =:id ");
			params.addValue("id", kriter.getId());
		}
		
		if(kriter != null && kriter.getDurum() != null){
			sql.append(" AND ");
			sql.append(" i.status =:durum ");
			params.addValue("durum", kriter.getDurum().getValue());
		}
		
		if(kriter != null && kriter.getAciklama() != null){
			sql.append(" AND ");
			sql.append(" i.aciklama LIKE :aciklama ");
			
			params.addValue("aciklama", "%"+kriter.getAciklama()+"%");
		}
		
		sql.append(" ORDER BY i.aciklama ");
		
		List<IlgiAlanlariParametreModel> liste = jdbctemplate.query(sql.toString(), 
				params,
				new IlgiAlanlariRowMapper());
		
		return liste;
	}
	
	@Override
//	@Cacheable(value="kullaniciilgialanlari",key="#username",unless="#result==null")
	public List<IlgiAlanlariParametreModel> getKullaniciIlgiAlanlari(String username) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" SELECT ");
		sql.append(" i.id,i.aciklama,i.olusturan,i.olusturma_tar,i.status,i.resim ");
		sql.append(" FROM ");
		sql.append(" kutuphanesistemi.ilgi_alanlari i ");
		sql.append(" JOIN kutuphanesistemi.user_ilgialanlari u on u.ilgialan_id=i.id ");
		sql.append(" WHERE u.username =:username ");
		sql.append(" ORDER BY i.olusturma_tar DESC ");
		
		params.addValue("username", username);
		
		List<IlgiAlanlariParametreModel> liste = jdbctemplate.query(sql.toString(),
				params,
				new IlgiAlanlariRowMapper());
		
		return liste;
	}
	
	@Override
	public ErrorDetail saveIlgiAlani(IlgiAlanlariParametreModel ilgiAlan) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		ErrorDetail errorDetail = null;
		
		sql.append(" INSERT INTO ");
		sql.append(" kutuphanesistemi.ilgi_alanlari ");
		sql.append(" ( ");
		sql.append(" aciklama,olusturan,olusturma_tar,status ");
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" :aciklama,:olusturan,CURDATE(),'AKTIF' ");
		sql.append(" ) ");
		
		params.addValue("aciklama", ilgiAlan.getAciklama());
		params.addValue("olusturan", ilgiAlan.getOlusturan().getUsername());
		
		int sonuc = jdbctemplate.update(sql.toString(), params);
		
		if(sonuc == 0){
			errorDetail = new ErrorDetail(500, "Kitap tür parametresi kaydedilirken hata meydana geldi!");
		}
		
		return errorDetail;
	}
	
	@Override
	public ErrorDetail updateIlgiAlani(IlgiAlanlariParametreModel ilgiAlan) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		ErrorDetail errorDetail = null;
		
		sql.append(" UPDATE ");
		sql.append(" kutuphanesistemi.ilgi_alanlari ");
		sql.append(" SET ");
		sql.append(" aciklama=:aciklama ,");
		sql.append(" status=:status, ");
		sql.append(" guncelleme_tar=CURDATE(), ");
		sql.append(" guncelleyen=:guncelleyen ");
		sql.append(" WHERE ");
		sql.append(" id=:id ");
		
		params.addValue("aciklama", ilgiAlan.getAciklama());
		params.addValue("status", ParametreDurumEnum.getEnumByValue(ilgiAlan.getDurum().getValue()).getValue());
		params.addValue("id", ilgiAlan.getId());
		params.addValue("guncelleyen", ilgiAlan.getGuncelleyen().getUsername());
		
		int sonuc = jdbctemplate.update(sql.toString(), params);
		
		if(sonuc == 0){
			errorDetail = new ErrorDetail(500, "İlgi alanı güncellenirken hata meydana geldi!");
		}else{
			net.sf.ehcache.CacheManager.getInstance().getCache("kitapturleri").removeAll();
		}
		
		return errorDetail;
	}
	
	@Override
	public ErrorDetail saveKullaniciIlgiAlanlari(KullaniciModel kullanici,
			List<IlgiAlanlariParametreModel> ilgiAlanListe) {
		ErrorDetail errorDetail = null;
		StringBuilder sql = new StringBuilder();
		int size = ilgiAlanListe.size();
		
		sql.append(" INSERT INTO kutuphanesistemi.user_ilgialanlari ");
		sql.append(" ( ");
		sql.append(" username,ilgialan_id ");
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" :username,:ilgialan_id ");
		sql.append(" ) ");
		
		List<Map<String, Object>> batchValues = new ArrayList<>(size);
		for(IlgiAlanlariParametreModel ilgiAlan:ilgiAlanListe){
			batchValues.add(
		            new MapSqlParameterSource("username", kullanici.getUsername())
		                    .addValue("ilgialan_id", ilgiAlan.getId())
		                    .getValues());
		}
		
		int[] updateCounts = jdbctemplate.batchUpdate(sql.toString(),
                batchValues.toArray(new Map[size]));
		
		if(updateCounts == null || updateCounts.length == 0){
			errorDetail = new ErrorDetail(500,"Kullanıcı ilgi alanları kaydedilirken hata meydana geldi!");
		}
		
		return errorDetail;
	}
	
	@Override
//	@Cacheable(value="yayinevleri",unless="#result==null")
	public List<YayineviModel> getYayinEviListe() {
		List<YayineviModel> liste = null;
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" SELECT ");
		sql.append(" y.ID,y.ACIKLAMA,y.OLUSTURMA_TAR,y.DURUM, ");
		sql.append(" u.username AS OLUSTURAN ");
		sql.append(" FROM kutuphanesistemi.kutuphane_param_yayinevi y ");
		sql.append(" JOIN kutuphanesistemi.users u ON y.OLUSTURAN=u.username ");
		sql.append(" WHERE y.DURUM=:durum ");
		sql.append(" ORDER BY y.ACIKLAMA ");
		
		params.addValue("durum", ParametreDurumEnum.AKTIF.getValue());
		
		liste = jdbctemplate.query(sql.toString(), params,new YayinEviModelRowMapper());
		
		return liste;
	}
	
	@Override
	public ErrorDetail saveYayinEviParam(YayineviModel model) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		ErrorDetail errorDetail = null;
		
		sql.append(" INSERT INTO ");
		sql.append(" kutuphanesistemi.kutuphane_param_yayinevi ");
		sql.append(" ( ");
		sql.append(" ACIKLAMA,OLUSTURAN,OLUSTURMA_TAR,DURUM ");
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" :aciklama,:olusturan,CURDATE(),:durum ");
		sql.append(" ) ");
		
		params.addValue("aciklama", model.getAciklama());
		params.addValue("olusturan", model.getOlusturan().getUsername());
		params.addValue("durum", model.getDurum().getValue());
		
		int sonuc = jdbctemplate.update(sql.toString(), params);
		
		if(sonuc == 0){
			errorDetail = new ErrorDetail(500, "Yayınevi parametresi kaydedilirken hata meydana geldi!");
		}
		
		return errorDetail;
	}
	
	@Override
	public ErrorDetail updateYayinEviParam(YayineviModel model) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		ErrorDetail errorDetail = null;
		
		sql.append(" UPDATE ");
		sql.append(" kutuphanesistemi.kutuphane_param_yayinevi ");
		sql.append(" SET durum=:durum ");
		sql.append(" WHERE ID=:p_id ");
		
		params.addValue("durum", model.getDurum().getValue());
		params.addValue("p_id", model.getId());
		
		int sonuc = jdbctemplate.update(sql.toString(), params);
		
		if(sonuc == 0){
			errorDetail = new ErrorDetail(500, "Yayınevi parametresi güncellenirken hata meydana geldi!");
		}
		
		return errorDetail;
	}
	
	@Override
	public void kullaniciilgiAlanSil(String useranme) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" DELETE FROM ");
		sql.append(" kutuphanesistemi.user_ilgialanlari ");
		sql.append(" WHERE username =:p_username ");
		
		params.addValue("p_username", useranme);
		
		jdbctemplate.update(sql.toString(), params);
		
	}
}

