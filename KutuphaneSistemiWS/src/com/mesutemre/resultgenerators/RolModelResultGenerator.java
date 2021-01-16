package com.mesutemre.resultgenerators;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mesutemre.enums.RoleTurEnum;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.rol.model.RolModel;

public class RolModelResultGenerator implements ResultSetExtractor{

	@Override
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		RolModel rolModel = new RolModel();
		
		rolModel.setId(rs.getInt("id"));
		rolModel.setRolKullanici(new KullaniciModel(rs.getString("username")));
		rolModel.setRol(RoleTurEnum.getEnumByValue(rs.getString("role")));
		rolModel.setOlusturan(new KullaniciModel(rs.getString("olusturanusername"), rs.getString("olusturanad"), rs.getString("olusturansoyad")));
		rolModel.setGuncelleyen(new KullaniciModel(rs.getString("guncelleyenusername"), rs.getString("guncelleyenad"), rs.getString("guncelleyensoyad")));
		rolModel.setOlusturmaTarihi(rs.getTimestamp("olusturmatar"));
		rolModel.setGuncellemeTarihi(rs.getTimestamp("guncellemetar"));
		
		return rolModel;
	}
	
}
