package com.mesutemre.kitap.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mesutemre.kitap.resultgenerator.KitapModelResultGenerator;

public class KitapModelRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		KitapModelResultGenerator resultGenerator = new KitapModelResultGenerator();
		return resultGenerator.extractData(rs);
	}
}
