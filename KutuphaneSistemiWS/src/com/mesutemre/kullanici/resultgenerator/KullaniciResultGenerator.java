package com.mesutemre.kullanici.resultgenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mesutemre.enums.CinsiyetEnum;
import com.mesutemre.kullanici.model.KullaniciModel;

/**
 * @author mesutemre.celenk
 *
 */
public class KullaniciResultGenerator implements ResultSetExtractor {
	
	private Environment env;
	
	public KullaniciResultGenerator() {
	}
	
	public KullaniciResultGenerator(Environment env) {
		this.env = env;
	}

	@Override
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		KullaniciModel model = new KullaniciModel();
		
		model.setUsername(rs.getString("username"));
		model.setAd(rs.getString("ad"));
		model.setSoyad(rs.getString("soyad"));
		model.setDogumTarihi(rs.getDate("dogumtarihi"));
		model.setEposta(rs.getString("eposta"));
		model.setCinsiyet(CinsiyetEnum.getEnumByValue(rs.getString("cinsiyet")));
		model.setResim(env.getProperty("kutuphanesistemi.resim.kullanici.contextPath")+"/"+rs.getString("resim"));
		model.setHaberdarmi(Boolean.valueOf(rs.getString("haberdarmi")));
		model.setEnabled(Boolean.valueOf(rs.getString("enabled")));
		
		return model;
	}
	
}
