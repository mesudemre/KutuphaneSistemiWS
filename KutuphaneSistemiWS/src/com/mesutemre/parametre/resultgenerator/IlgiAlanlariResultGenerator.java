package com.mesutemre.parametre.resultgenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mesutemre.enums.ParametreDurumEnum;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;

public class IlgiAlanlariResultGenerator implements ResultSetExtractor{

	@Override
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		IlgiAlanlariParametreModel model = new IlgiAlanlariParametreModel();
		
		model.setId(rs.getInt("id"));
		model.setAciklama(rs.getString("aciklama"));
		model.setOlusturan(new KullaniciModel(rs.getString("olusturan")));
		model.setOlusturmaTarihi(rs.getDate("olusturma_tar"));
		model.setDurum(ParametreDurumEnum.getEnumByValue(rs.getString("status")));
		model.setResim(rs.getString("resim"));
		
		return model;
	}
}
