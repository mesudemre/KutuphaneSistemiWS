package com.mesutemre.kitap.model;

import java.io.Serializable;
import java.util.Date;

import com.mesutemre.kullanici.model.KullaniciModel;

public class KitapYorumModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private KitapModel kitap;
	private String yorum;
	private KullaniciModel olusturan;
	private Date olusturmaTar;
	private int status;
	private int puan;
	private String kullaniciResim;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public KitapModel getKitap() {
		return kitap;
	}

	public void setKitap(KitapModel kitap) {
		this.kitap = kitap;
	}

	public String getYorum() {
		return yorum;
	}

	public void setYorum(String yorum) {
		this.yorum = yorum;
	}

	public KullaniciModel getOlusturan() {
		return olusturan;
	}

	public void setOlusturan(KullaniciModel olusturan) {
		this.olusturan = olusturan;
	}

	public Date getOlusturmaTar() {
		return olusturmaTar;
	}

	public void setOlusturmaTar(Date olusturmaTar) {
		this.olusturmaTar = olusturmaTar;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPuan() {
		return puan;
	}

	public void setPuan(int puan) {
		this.puan = puan;
	}

	public String getKullaniciResim() {
		return kullaniciResim;
	}

	public void setKullaniciResim(String kullaniciResim) {
		this.kullaniciResim = kullaniciResim;
	}
}
