package com.mesutemre.parametre.resultgenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mesutemre.enums.ParametreDurumEnum;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.parametre.model.YayineviModel;

public class YayinEviModelResultGenerator implements ResultSetExtractor{

	@Override
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		YayineviModel model = new YayineviModel();
		
		model.setId(rs.getInt("ID"));
		model.setAciklama(rs.getString("ACIKLAMA"));
		model.setOlusturan(new KullaniciModel(rs.getString("OLUSTURAN")));
		model.setOlusturmaTarihi(rs.getDate("OLUSTURMA_TAR"));
		model.setDurum(ParametreDurumEnum.getenumByLabel(rs.getString("DURUM")));
		
		return model;
	}
}
