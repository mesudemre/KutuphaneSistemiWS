package com.mesutemre.log.model;

import com.mesutemre.enums.KitapIslemEnum;
import com.mesutemre.kullanici.model.KullaniciModel;

public class LogModel {

	private KitapIslemEnum islem;
	private KullaniciModel islemYapan;
	private int kitapId;

	public LogModel(KitapIslemEnum islem,KullaniciModel islemYapan,int kitapId) {
		this.islem = islem;
		this.islemYapan = islemYapan;
		this.kitapId = kitapId;
	}
	
	public LogModel() {
	}
	
	public KitapIslemEnum getIslem() {
		return islem;
	}

	public void setIslem(KitapIslemEnum islem) {
		this.islem = islem;
	}

	public KullaniciModel getIslemYapan() {
		return islemYapan;
	}

	public void setIslemYapan(KullaniciModel islemYapan) {
		this.islemYapan = islemYapan;
	}

	public int getKitapId() {
		return kitapId;
	}

	public void setKitapId(int kitapId) {
		this.kitapId = kitapId;
	}
	
}
