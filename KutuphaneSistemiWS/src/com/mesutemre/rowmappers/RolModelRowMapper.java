package com.mesutemre.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mesutemre.resultgenerators.RolModelResultGenerator;

public class RolModelRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		RolModelResultGenerator resultGenerator = new RolModelResultGenerator();
		return resultGenerator.extractData(rs);
	}
}
