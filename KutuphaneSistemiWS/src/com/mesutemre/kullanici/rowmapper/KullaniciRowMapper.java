package com.mesutemre.kullanici.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mesutemre.kullanici.resultgenerator.KullaniciResultGenerator;

/**
 * @author mesutemre.celenk
 *
 */
public class KullaniciRowMapper implements RowMapper {
	
	@Override
	public Object mapRow(ResultSet rs, int i) throws SQLException {
		KullaniciResultGenerator resultGenerator = new KullaniciResultGenerator();
		return resultGenerator.extractData(rs);
	}

}
