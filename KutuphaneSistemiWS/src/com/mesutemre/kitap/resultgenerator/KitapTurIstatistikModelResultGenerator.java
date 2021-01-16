package com.mesutemre.kitap.resultgenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mesutemre.kitap.model.KitapTurIstatistikModel;

public class KitapTurIstatistikModelResultGenerator implements ResultSetExtractor{

	@Override
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		KitapTurIstatistikModel model = new KitapTurIstatistikModel();
		
		model.setAciklama(rs.getString("aciklama"));
		model.setAdet(rs.getInt("sayi"));
		
		return model;
	}
}
