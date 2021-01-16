package com.mesutemre.kitap.model;

import java.util.Date;

import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;
import com.mesutemre.parametre.model.YayineviModel;

public class KitapModel {

	private int id;
	private String kitapAd;
	private String yazarAd;
	private IlgiAlanlariParametreModel kitapTur;
	private YayineviModel yayinEvi;
	
	private Date alinmatarihi;
	
	private Date kayittarihi;
	private int minKayitNum;
	private int maxKayitNum;
	private String kitapResimPath;
	private String kitapAciklama;
	private int paginationNum;
	private float kitapPuan;
	
	public KitapModel() {
	}
	
	public KitapModel(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKitapAd() {
		return kitapAd;
	}

	public void setKitapAd(String kitapAd) {
		this.kitapAd = kitapAd;
	}

	public String getYazarAd() {
		return yazarAd;
	}

	public void setYazarAd(String yazarAd) {
		this.yazarAd = yazarAd;
	}

	public IlgiAlanlariParametreModel getKitapTur() {
		return kitapTur;
	}

	public void setKitapTur(IlgiAlanlariParametreModel kitapTur) {
		this.kitapTur = kitapTur;
	}

	public YayineviModel getYayinEvi() {
		return yayinEvi;
	}

	public void setYayinEvi(YayineviModel yayinEvi) {
		this.yayinEvi = yayinEvi;
	}

	public Date getAlinmatarihi() {
		return alinmatarihi;
	}

	public void setAlinmatarihi(Date alinmatarihi) {
		this.alinmatarihi = alinmatarihi;
	}

	public Date getKayittarihi() {
		return kayittarihi;
	}

	public void setKayittarihi(Date kayittarihi) {
		this.kayittarihi = kayittarihi;
	}

	public int getMinKayitNum() {
		return minKayitNum;
	}

	public void setMinKayitNum(int minKayitNum) {
		this.minKayitNum = minKayitNum;
	}

	public int getMaxKayitNum() {
		return maxKayitNum;
	}

	public void setMaxKayitNum(int maxKayitNum) {
		this.maxKayitNum = maxKayitNum;
	}

	public String getKitapResimPath() {
		return kitapResimPath;
	}

	public void setKitapResimPath(String kitapResimPath) {
		this.kitapResimPath = kitapResimPath;
	}

	public String getKitapAciklama() {
		return kitapAciklama;
	}

	public void setKitapAciklama(String kitapAciklama) {
		this.kitapAciklama = kitapAciklama;
	}

	public int getPaginationNum() {
		return paginationNum;
	}

	public void setPaginationNum(int paginationNum) {
		this.paginationNum = paginationNum;
	}

	public float getKitapPuan() {
		return kitapPuan;
	}

	public void setKitapPuan(float kitapPuan) {
		this.kitapPuan = kitapPuan;
	}
}
