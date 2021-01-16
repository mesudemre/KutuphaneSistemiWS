package com.mesutemre.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mesutemre.annotations.MyDateFormat;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.util.CustomJsonDateSerializer;

/**
 * @author mesutemre.celenk
 *
 */
public class BaseModel {

	private KullaniciModel olusturan;
	private KullaniciModel guncelleyen;
	
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	@MyDateFormat(value="yyyy-MM-dd")
	private Date olusturmaTarihi;
	
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	@MyDateFormat(value="yyyy-MM-dd")
	private Date guncellemeTarihi;

	public KullaniciModel getOlusturan() {
		return olusturan;
	}

	public void setOlusturan(KullaniciModel olusturan) {
		this.olusturan = olusturan;
	}

	public KullaniciModel getGuncelleyen() {
		return guncelleyen;
	}

	public void setGuncelleyen(KullaniciModel guncelleyen) {
		this.guncelleyen = guncelleyen;
	}

	public Date getOlusturmaTarihi() {
		return olusturmaTarihi;
	}

	public void setOlusturmaTarihi(Date olusturmaTarihi) {
		this.olusturmaTarihi = olusturmaTarihi;
	}

	public Date getGuncellemeTarihi() {
		return guncellemeTarihi;
	}

	public void setGuncellemeTarihi(Date guncellemeTarihi) {
		this.guncellemeTarihi = guncellemeTarihi;
	}

}
