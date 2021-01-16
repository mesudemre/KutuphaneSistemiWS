package com.mesutemre.kitap.resultgenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mesutemre.kitap.model.KitapModel;
import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;
import com.mesutemre.parametre.model.YayineviModel;

public class KitapModelResultGenerator implements ResultSetExtractor{

	@Override
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		KitapModel model = new KitapModel();
		
		model.setId(rs.getInt("id"));
		model.setKitapAd(rs.getString("kitapad"));
		model.setYazarAd(rs.getString("yazarad"));
		model.setAlinmatarihi(rs.getDate("alinmatarihi"));
		model.setKayittarihi(rs.getDate("kayittarihi"));
		model.setKitapTur(new IlgiAlanlariParametreModel(rs.getInt("kitapturıd"),rs.getString("kitaptur")));
		model.setYayinEvi(new YayineviModel(rs.getInt("yayinEviId"), rs.getString("yayinevi")));
		model.setKitapAciklama(rs.getString("KITAP_ACIKLAMA"));
		model.setKitapResimPath(rs.getString("KITAP_IMG_URL"));
		model.setKitapPuan(rs.getFloat("PUAN"));
		
		
		return model;
	}
}
