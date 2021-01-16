package com.mesutemre.kitap.resultgenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mesutemre.kitap.model.KitapPuanModel;

public class KitapPuanModelResultGenerator implements ResultSetExtractor{

	@Override
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		KitapPuanModel model = new KitapPuanModel();
		
		model.setPuan(rs.getInt("PUAN"));
		model.setAdet(rs.getInt("ADET"));
		
		return model;
	}
	
}
