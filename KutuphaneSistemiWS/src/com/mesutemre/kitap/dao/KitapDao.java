package com.mesutemre.kitap.dao;

import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mesutemre.kitap.model.KitapModel;
import com.mesutemre.kitap.model.KitapPuanModel;
import com.mesutemre.kitap.model.KitapTurIstatistikModel;
import com.mesutemre.kitap.model.KitapYorumModel;
import com.mesutemre.kitap.rowmapper.KitapModelRowMapper;
import com.mesutemre.kitap.rowmapper.KitapPuanModelRowMapper;
import com.mesutemre.kitap.rowmapper.KitapTurIstatistikModelRowMapper;
import com.mesutemre.kitap.rowmapper.KitapYorumModelRowMapper;
import com.mesutemre.kullanici.model.KullaniciModel;

@Repository
public class KitapDao implements IKitapDao{
	
	private NamedParameterJdbcTemplate jdbctemplate;
	
	@Autowired
	private Environment env;
	
	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbctemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<KitapModel> getKitapListeByKriter(KitapModel kriter,KullaniciModel kullanici) {
		List<KitapModel> liste = null;
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" SELECT ");
		sql.append(" k.id,k.kitapad, k.yazarad,k.kayittarihi,k.alinmatarihi,k.alinanyer, ");
		sql.append(" ia.aciklama as kitaptur,ia.id as kitapturId, ");
		sql.append(" y.ACIKLAMA as yayinevi,y.ID as yayinEviId,k.KITAP_ACIKLAMA,k.KITAP_IMG_URL, ");
		sql.append(" kutuphanesistemi.GET_KITAP_ORTALAMA_PUAN(k.id) as PUAN ");
		sql.append(" FROM ");
		sql.append(" kutuphanesistemi.kitaplar k ");
		sql.append(" JOIN kutuphanesistemi.ilgi_alanlari ia ON k.kitaptur=ia.id ");
		sql.append(" JOIN kutuphanesistemi.kutuphane_param_yayinevi y ON k.yayinevi=y.ID ");
		sql.append(" WHERE 1=1 ");
		
		params.addValue("p_puanKullanici", kullanici.getUsername());
		
		if(kriter != null && kriter.getId()>0){
			sql.append(" AND ");
			sql.append(" k.id=:p_id ");
			
			params.addValue("p_id", kriter.getId());
		}
		
		if(kriter != null && kriter.getKitapTur() != null && kriter.getKitapTur().getId()>0){
			sql.append(" AND ");
			sql.append(" ia.id=:ia_id ");
			
			params.addValue("ia_id", kriter.getKitapTur().getId());
		}
		
		if(kriter != null && kriter.getYayinEvi() != null && kriter.getYayinEvi().getId()>0){
			sql.append(" AND ");
			sql.append(" y.ID=:y_id ");
			
			params.addValue("y_id", kriter.getYayinEvi().getId());
		}
		
		
		
		
		if(kriter != null && (kriter.getKitapAd()!= null || kriter.getYazarAd() != null)) {
			String query = null;
			if(kriter.getKitapAd() != null) {
				query = kriter.getKitapAd();
			}else {
				query = kriter.getYazarAd();
			}
			sql.append(" AND ");
			sql.append(" UPPER(k.kitapad) like :p_query OR UPPER(k.yazarad) like :p_query ");
			
			params.addValue("p_query", "%"+query.toUpperCase(new Locale("TR"))+"%");
		}
		
		sql.append(" ORDER BY k.alinmatarihi DESC ");
		
		if(kriter.getMinKayitNum()>-1 && kriter.getMaxKayitNum()>0){
			sql.append(" LIMIT :p_bas,:p_son");
			
			params.addValue("p_bas", kriter.getMinKayitNum());
			params.addValue("p_son", kriter.getMaxKayitNum());
		}
		
		liste = jdbctemplate.query(sql.toString(), params,new KitapModelRowMapper());
		
		return liste;
	}
	
	@Override
	public List<KitapTurIstatistikModel> getKitapTurIstatistik() {
		List<KitapTurIstatistikModel> liste = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT ia.aciklama,COUNT(k.id) as sayi from kitaplar k ");
		sql.append(" JOIN kutuphanesistemi.ilgi_alanlari ia ON ia.id=k.kitaptur ");
		sql.append(" GROUP BY k.kitaptur ");
		
		liste = jdbctemplate.query(sql.toString(), new KitapTurIstatistikModelRowMapper());
		
		return liste;
	}
	
	@Override
	public int kitapKaydet(KitapModel model) {
		StringBuilder sql = new StringBuilder();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" INSERT INTO ");
		sql.append(" kutuphanesistemi.kitaplar ");
		sql.append(" ( ");
		sql.append(" 	kitapad,yazarad,kitaptur,alinmatarihi,yayinevi,kayittarihi,KITAP_ACIKLAMA ");
		sql.append(" ) ");
		sql.append(" 	VALUES ");
		sql.append(" ( ");
		sql.append(" 	:p_kitapAd,:p_yazarAd,:p_kitapTur,:p_alinmaTar,:p_yayinEvi,CURDATE(),:p_kitapAciklama ");
		sql.append(" ) ");
		
		params.addValue("p_kitapAd", model.getKitapAd());
		params.addValue("p_yazarAd", model.getYazarAd());
		params.addValue("p_kitapTur", model.getKitapTur().getId());
		params.addValue("p_alinmaTar",model.getAlinmatarihi());
		params.addValue("p_yayinEvi", model.getYayinEvi().getId());
		params.addValue("p_kitapAciklama", model.getKitapAciklama());
		
		jdbctemplate.update(sql.toString(), params,keyHolder);
		int sonuc = keyHolder.getKey().intValue();
		
		return sonuc;
	}
	
	@Override
	public void kitapResimUrlGuncelle(int kitapId, String url) {
		String sql = "UPDATE kutuphanesistemi.kitaplar SET KITAP_IMG_URL=:p_imageUrl WHERE ID=:p_id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("p_imageUrl", url);
		params.addValue("p_id", kitapId);
		
		jdbctemplate.update(sql.toString(), params);
	}
	
	@Override
	public int kitapPuanKaydet(KitapPuanModel model) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" INSERT INTO ");
		sql.append(" kutuphanesistemi.kitap_puan ");
		sql.append(" ( ");
		sql.append(" 	kitap_id,puan,olusturan,olusturma_tar ");
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" 	:p_kitapId,:p_puan,:p_olusturan,CURDATE() ");
		sql.append(" ) ");
		
		params.addValue("p_kitapId", model.getKitap().getId());
		params.addValue("p_puan", model.getPuan());
		params.addValue("p_olusturan", model.getOlusturan().getUsername());
		
		return jdbctemplate.update(sql.toString(), params);
	}
	
	@Override
	public int kitapPuanGuncelle(KitapPuanModel model) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" UPDATE ");
		sql.append(" kutuphanesistemi.kitap_puan ");
		sql.append(" SET puan=:p_puan,guncelleyen=:p_guncelleyen,guncelleme_tar=CURDATE() ");
		sql.append(" WHERE olusturan=:p_guncelleyen AND kitap_id=:p_kitapId ");
		
		params.addValue("p_puan", model.getPuan());
		params.addValue("p_guncelleyen", model.getGuncelleyen().getUsername());
		params.addValue("p_kitapId", model.getKitap().getId());
		
		return jdbctemplate.update(sql.toString(), params);
	}
	
	@Override
	public int kitapYorumKaydet(KitapYorumModel model) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" INSERT INTO ");
		sql.append(" kutuphanesistemi.KITAP_YORUM ");
		sql.append(" ( ");
		sql.append(" 	kitap_id,yorum,olusturan,olusturma_tar,status ");
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" 	:p_kitapId,:p_yorum,:p_olusturan,CURDATE(),1 ");
		sql.append(" ) ");
		
		params.addValue("p_kitapId", model.getKitap().getId());
		params.addValue("p_yorum", model.getYorum());
		params.addValue("p_olusturan", model.getOlusturan().getUsername());
		
		return jdbctemplate.update(sql.toString(), params);
	}
	
	@Override
	public int kitapYorumGuncelle(KitapYorumModel model) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" UPDATE ");
		sql.append(" kutuphanesistemi.KITAP_YORUM ");
		sql.append(" SET yorum=:p_yorum,status=:p_status ");
		sql.append(" WHERE ID=:p_id ");
		
		params.addValue("p_yorum", model.getYorum());
		params.addValue("p_status", model.getStatus());
		params.addValue("p_id", model.getId());
		
		return jdbctemplate.update(sql.toString(), params);
	}
	
	@Override
	public List<KitapPuanModel> getAdetliPuanListeByKitapId(int kitapId) {
		List<KitapPuanModel> liste = null;
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" SELECT ");
		sql.append(" COUNT(ID) AS ADET,PUAN ");
		sql.append(" FROM ");
		sql.append(" kutuphanesistemi.kitap_puan ");
		sql.append(" where kitap_id=:p_kitapId ");
		sql.append(" GROUP BY puan ");
		
		params.addValue("p_kitapId", kitapId);
		
		liste = jdbctemplate.query(sql.toString(), params,new KitapPuanModelRowMapper());
		
		return liste;
	}
	
	@Override
	public List<KitapYorumModel> getKitapYorumlariByKitapId(int kitapId) {
		List<KitapYorumModel> liste = null;
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" SELECT ");
		sql.append(" y.ID,y.YORUM,u.username as OLUSTURAN,y.OLUSTURMA_TAR, ");
		sql.append(" u.ad as olusturan_ad,u.soyad as olusturan_soyad ");
		sql.append(" FROM ");
		sql.append(" KITAP_YORUM y ");
		sql.append(" JOIN users u ON y.OLUSTURAN=u.username ");
		sql.append(" WHERE y.KITAP_ID=:p_kitapId AND y.STATUS=1 ORDER BY y.ID DESC ");
		
		params.addValue("p_kitapId", kitapId);
		
		liste = jdbctemplate.query(sql.toString(), params,new KitapYorumModelRowMapper(env));
		
		return liste;
	}
}
