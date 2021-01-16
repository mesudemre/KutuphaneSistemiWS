package com.mesutemre.log.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mesutemre.log.model.LogModel;
import com.mesutemre.model.ErrorDetail;

@Repository
public class LogDao implements ILogDao {

	private NamedParameterJdbcTemplate jdbctemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbctemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public ErrorDetail logKaydet(LogModel model) {
		ErrorDetail errorDetail = null;
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		sql.append(" INSERT INTO ");
		sql.append(" kutuphanesistemi.kitap_islem_log ");
		sql.append(" ( ");
		sql.append(" 	islem,aciklama,kullanici,islem_tarih,kitap_id ");
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" 	:p_islem,:p_aciklama,:p_kullanici,NOW(),:p_kitapId ");
		sql.append(" ) ");
		
		params.addValue("p_islem", model.getIslem().getValue());
		params.addValue("p_aciklama", model.getIslem().getLabel());
		params.addValue("p_kullanici", model.getIslemYapan().getUsername());
		params.addValue("p_kitapId", model.getKitapId());
		
		int sonuc = jdbctemplate.update(sql.toString(), params);
		
		if(sonuc == 0){
			errorDetail = new ErrorDetail(500, "Log kaydı esnasında hata meydana geldi!");
		}
		
		
		return errorDetail;
	}
}
