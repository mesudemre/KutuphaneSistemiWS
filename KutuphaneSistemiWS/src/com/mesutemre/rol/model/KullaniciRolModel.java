package com.mesutemre.rol.model;

import java.util.List;

import com.mesutemre.kullanici.model.KullaniciModel;

public class KullaniciRolModel {

	private RolModel rol;
	private List<KullaniciModel> kullaniciListe;

	public RolModel getRol() {
		return rol;
	}

	public void setRol(RolModel rol) {
		this.rol = rol;
	}

	public List<KullaniciModel> getKullaniciListe() {
		return kullaniciListe;
	}

	public void setKullaniciListe(List<KullaniciModel> kullaniciListe) {
		this.kullaniciListe = kullaniciListe;
	}

}
