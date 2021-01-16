package com.mesutemre.sayfa.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mesutemre.sayfa.resultgenerator.SayfaModelResultGenerator;

public class SayfaModelRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int i) throws SQLException {
		SayfaModelResultGenerator resultGenerator = new SayfaModelResultGenerator();
		return resultGenerator.extractData(rs);
	}
}
