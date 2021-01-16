package com.mesutemre.rol.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.rol.model.KullaniciRolModel;
import com.mesutemre.rol.model.RolModel;
import com.mesutemre.rowmappers.RolModelRowMapper;

@Repository
public class RolDao implements IRolDao{

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public List<RolModel> getKullaniciRolListe(RolModel kriter) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" select ");
		sql.append(" ur.id,ur.username,ur.role, ");
		sql.append(" olusturan.username as olusturanusername, ");
		sql.append(" olusturan.ad as olusturanad,olusturan.soyad as olusturansoyad, ");
		sql.append(" ur.olusturmatar, ");
		sql.append(" guncelleyen.username as guncelleyenusername, ");
		sql.append(" guncelleyen.ad as guncelleyenad,guncelleyen.soyad as guncelleyensoyad, ");
		sql.append(" ur.guncellemetar ");
		sql.append(" FROM ");
		sql.append(" kutuphanesistemi.user_roles ur ");
		sql.append(" LEFT OUTER JOIN kutuphanesistemi.users olusturan on ur.olusturan=olusturan.username ");
		sql.append(" LEFT OUTER JOIN kutuphanesistemi.users guncelleyen on ur.guncelleyen=guncelleyen.username ");
		sql.append(" WHERE 1=1 ");
		
		if(kriter.getId()>0){
			sql.append(" AND ");
			sql.append(" ur.id =:id ");
			params.addValue("username", kriter.getId());
		}
		
		if(kriter.getRolKullanici() != null && kriter.getRolKullanici().getUsername() != null){
			sql.append(" AND ");
			sql.append(" ur.username =:username ");
			params.addValue("username", kriter.getRolKullanici().getUsername());
		}
		
		List<RolModel> roller = jdbcTemplate.query(sql.toString(), 
				params,
				new RolModelRowMapper());
		
		return roller;
	}
	
	@Override
	public ErrorDetail insertRol(RolModel rol) {
		ErrorDetail errorDetail = null;
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" INSERT INTO kutuphanesistemi.user_roles ");
		sql.append(" ( ");
		sql.append(" username,role,olusturmatar,olusturan ");
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" :username,:role,CURDATE(),:olusturan ");
		sql.append(" ) ");
		
		params.addValue("username", rol.getRolKullanici().getUsername());
		params.addValue("role", rol.getRol().getValue());
		params.addValue("olusturan", rol.getOlusturan().getUsername());
		
		int result = jdbcTemplate.update(sql.toString(), params);
		
		if(result == 0){
			errorDetail = new ErrorDetail(500, "Rol bilgisi kaydedilirken hata meydana geldi!");
		}
		
		return errorDetail;
	}
	
	@Override
	public ErrorDetail kullaniciRolKaydet(KullaniciRolModel kullaniciRolModel){
		ErrorDetail errorDetail = null;
		StringBuilder sql = new StringBuilder();
		int listeSize = kullaniciRolModel.getKullaniciListe().size();
		
		for(KullaniciModel model:kullaniciRolModel.getKullaniciListe()){
			this.kullaniciRolSil(model);
		}
		
		sql.append(" INSERT INTO kutuphanesistemi.user_roles ");
		sql.append(" ( ");
		sql.append(" username,role,olusturmatar,olusturan ");
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" :username,:role,CURDATE(),:olusturan ");
		sql.append(" ) ");
		
		
		List<Map<String, Object>> batchValues = new ArrayList<>(listeSize);
		for(KullaniciModel model:kullaniciRolModel.getKullaniciListe()){
			batchValues.add(
		            new MapSqlParameterSource("username", model.getUsername())
		                    .addValue("role", kullaniciRolModel.getRol().getRol().getValue())
		                    .addValue("olusturan", kullaniciRolModel.getRol().getOlusturan().getUsername())
		                    .getValues());
		}
		
		int[] updateCounts = jdbcTemplate.batchUpdate(sql.toString(),
                batchValues.toArray(new Map[listeSize]));
		
		if(updateCounts == null || updateCounts.length == 0){
			errorDetail = new ErrorDetail(500,"Kullanıcıların rolleri kaydedilirken hata meydana geldi!");
		}
		
		return errorDetail;
	}
	
	@Override
	public void kullaniciRolSil(KullaniciModel model) {
		ErrorDetail errorDetail = null;
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" DELETE FROM kutuphanesistemi.user_roles WHERE username=:username ");
		
		params.addValue("username", model.getUsername());
		
		jdbcTemplate.update(sql.toString(), params);
		
	}
}
