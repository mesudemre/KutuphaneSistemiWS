package com.mesutemre.kitap.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mesutemre.kitap.resultgenerator.KitapPuanModelResultGenerator;

public class KitapPuanModelRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		KitapPuanModelResultGenerator resultGenerator = new KitapPuanModelResultGenerator();
		return resultGenerator.extractData(rs);
	}
}
