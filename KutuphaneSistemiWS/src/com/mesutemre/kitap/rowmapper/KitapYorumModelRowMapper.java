package com.mesutemre.kitap.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;

import com.mesutemre.kitap.resultgenerator.KitapYorumModelResultGenerator;

public class KitapYorumModelRowMapper implements RowMapper{

	private Environment env;
	
	public KitapYorumModelRowMapper(Environment env) {
		this.env = env;
	}

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		KitapYorumModelResultGenerator resultGenerator = new KitapYorumModelResultGenerator(env);
		return resultGenerator.extractData(rs);
	}
}
