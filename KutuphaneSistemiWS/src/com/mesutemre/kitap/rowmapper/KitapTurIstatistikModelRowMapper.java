package com.mesutemre.kitap.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mesutemre.kitap.resultgenerator.KitapTurIstatistikModelResultGenerator;

public class KitapTurIstatistikModelRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		KitapTurIstatistikModelResultGenerator resultGenerator = new KitapTurIstatistikModelResultGenerator();
		return resultGenerator.extractData(rs);
	}
}
