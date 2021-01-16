package com.mesutemre.kitap.model;

import java.io.Serializable;
import java.util.Date;

import com.mesutemre.kullanici.model.KullaniciModel;

public class KitapPuanModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private KitapModel kitap;
	private int puan;
	private KullaniciModel olusturan;
	private Date olusturmaTar;
	private KullaniciModel guncelleyen;
	private Date guncellemeTar;
	private double ortalamaPuan;
	private int adet;
	
	public KitapPuanModel() {
	}
	
	public KitapPuanModel(int puan,int adet) {
		this.puan = puan;
		this.adet = adet;
	}

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

	public int getPuan() {
		return puan;
	}

	public void setPuan(int puan) {
		this.puan = puan;
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

	public KullaniciModel getGuncelleyen() {
		return guncelleyen;
	}

	public void setGuncelleyen(KullaniciModel guncelleyen) {
		this.guncelleyen = guncelleyen;
	}

	public Date getGuncellemeTar() {
		return guncellemeTar;
	}

	public void setGuncellemeTar(Date guncellemeTar) {
		this.guncellemeTar = guncellemeTar;
	}

	public double getOrtalamaPuan() {
		return ortalamaPuan;
	}

	public void setOrtalamaPuan(double ortalamaPuan) {
		this.ortalamaPuan = ortalamaPuan;
	}

	public int getAdet() {
		return adet;
	}

	public void setAdet(int adet) {
		this.adet = adet;
	}
}
