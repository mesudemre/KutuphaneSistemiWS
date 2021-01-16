package com.mesutemre.kullanici.dao;

import java.util.List;

import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.model.ErrorDetail;

public interface IKullaniciDao {

	public KullaniciModel 		getKullaniciBilgi(String username);
	public ErrorDetail			kullaniciKaydet(KullaniciModel model);
	public List<KullaniciModel> kullaniciFiltrele(String username);
	public ErrorDetail			kullaniciGuncelle(KullaniciModel model);
	public void					kullaniciResimGuncelle(String username);
	
}
