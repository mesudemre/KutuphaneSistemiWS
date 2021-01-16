package com.mesutemre.kitap.model;

import java.util.List;

public class YorumListeModel {

	private List<KitapPuanModel> puanListe;
	private List<KitapYorumModel> yorumListe;

	public List<KitapPuanModel> getPuanListe() {
		return puanListe;
	}

	public void setPuanListe(List<KitapPuanModel> puanListe) {
		this.puanListe = puanListe;
	}

	public List<KitapYorumModel> getYorumListe() {
		return yorumListe;
	}

	public void setYorumListe(List<KitapYorumModel> yorumListe) {
		this.yorumListe = yorumListe;
	}

}
