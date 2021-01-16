package com.mesutemre.kitap.model;

import java.io.Serializable;

public class KitapTurIstatistikModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String aciklama;
	private int adet;
	
	public String getAciklama() {
		return aciklama;
	}
	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}
	public int getAdet() {
		return adet;
	}
	public void setAdet(int adet) {
		this.adet = adet;
	}
	
	
}
