package com.mesutemre.kitap.resultgenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mesutemre.kitap.model.KitapYorumModel;
import com.mesutemre.kullanici.model.KullaniciModel;

public class KitapYorumModelResultGenerator implements ResultSetExtractor{
	
	private Environment env;
	
	public KitapYorumModelResultGenerator(Environment env) {
		this.env = env;
	}

	@Override
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		KitapYorumModel model = new KitapYorumModel();
		
		model.setId(rs.getInt("ID"));
		model.setYorum(rs.getString("YORUM"));
		model.setOlusturan(new KullaniciModel(rs.getString("OLUSTURAN"), rs.getString("OLUSTURAN_AD"), rs.getString("OLUSTURAN_SOYAD")));
		model.setKullaniciResim(env.getProperty("kutuphanesistemi.resim.kullanici.contextPath")+model.getOlusturan().getUsername());
		model.setOlusturmaTar(rs.getDate("OLUSTURMA_TAR"));
		
		return model;
	}
}
