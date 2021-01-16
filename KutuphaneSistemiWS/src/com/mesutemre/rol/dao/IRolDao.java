package com.mesutemre.rol.dao;

import java.util.List;

import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.rol.model.KullaniciRolModel;
import com.mesutemre.rol.model.RolModel;

public interface IRolDao {

	public List<RolModel> 	getKullaniciRolListe(RolModel kriter);
	public ErrorDetail 		insertRol(RolModel rol);
	public ErrorDetail 		kullaniciRolKaydet(KullaniciRolModel kullaniciRolModel);
	public void				kullaniciRolSil(KullaniciModel model);
}
