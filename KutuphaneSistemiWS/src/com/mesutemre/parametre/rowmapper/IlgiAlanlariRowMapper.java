package com.mesutemre.parametre.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mesutemre.parametre.resultgenerator.IlgiAlanlariResultGenerator;

public class IlgiAlanlariRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int i) throws SQLException {
		IlgiAlanlariResultGenerator resultGenerator = new IlgiAlanlariResultGenerator();
		return resultGenerator.extractData(rs);
	}
}
