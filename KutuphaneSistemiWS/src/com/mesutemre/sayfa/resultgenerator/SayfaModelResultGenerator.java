package com.mesutemre.sayfa.resultgenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mesutemre.enums.ParametreDurumEnum;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.sayfa.model.SayfaModel;

public class SayfaModelResultGenerator implements ResultSetExtractor{

	@Override
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		SayfaModel model = new SayfaModel();
		
		model.setId(rs.getInt("id"));
		model.setSayfaAd(rs.getString("sayfaad"));
		model.setAngularRouteName(rs.getString("angularroutename"));
		model.setDurum(ParametreDurumEnum.getEnumByValue(rs.getString("status")));
		model.setOlusturan(new KullaniciModel(rs.getString("username"), rs.getString("ad"), rs.getString("soyad")));
		model.setOlusturmaTarihi(rs.getDate("olusturmatarihi"));
		
		return model;
	}
}
