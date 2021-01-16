package com.mesutemre.parametre.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mesutemre.parametre.resultgenerator.YayinEviModelResultGenerator;

public class YayinEviModelRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		YayinEviModelResultGenerator resultGenerator = new YayinEviModelResultGenerator();
		
		return resultGenerator.extractData(rs);
	}
}
