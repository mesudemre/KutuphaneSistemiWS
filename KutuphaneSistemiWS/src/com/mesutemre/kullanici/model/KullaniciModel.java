package com.mesutemre.kullanici.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mesutemre.annotations.MyDateFormat;
import com.mesutemre.enums.CinsiyetEnum;
import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;
import com.mesutemre.rol.model.RolModel;
import com.mesutemre.util.CustomJsonDateSerializer;

/**
 * @author mesutemre.celenk
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class KullaniciModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String ad;
	private String soyad;
	
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	@MyDateFormat(value="yyyy-MM-dd")
	private Date dogumTarihi;
	
	private String eposta;
	
	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	private CinsiyetEnum cinsiyet;
	
	private String resim;
	private boolean haberdarmi;
	private List<IlgiAlanlariParametreModel> ilgiAlanlari;
	private boolean enabled;
	private List<RolModel> roller = new ArrayList<RolModel>();
	
	public KullaniciModel() {
	}
	
	public KullaniciModel(String username) {
		this.username = username;
	}
	
	public KullaniciModel(String username,String ad,String soyad) {
		this.username = username;
		this.ad = ad;
		this.soyad = soyad;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getSoyad() {
		return soyad;
	}
	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}
	public Date getDogumTarihi() {
		return dogumTarihi;
	}
	public void setDogumTarihi(Date dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}
	public String getEposta() {
		return eposta;
	}
	public void setEposta(String eposta) {
		this.eposta = eposta;
	}
	public CinsiyetEnum getCinsiyet() {
		return cinsiyet;
	}
	public void setCinsiyet(CinsiyetEnum cinsiyet) {
		this.cinsiyet = cinsiyet;
	}
	public String getResim() {
		return resim;
	}
	public void setResim(String resim) {
		this.resim = resim;
	}
	public boolean isHaberdarmi() {
		return haberdarmi;
	}
	public void setHaberdarmi(boolean haberdarmi) {
		this.haberdarmi = haberdarmi;
	}
	public List<IlgiAlanlariParametreModel> getIlgiAlanlari() {
		return ilgiAlanlari;
	}
	public void setIlgiAlanlari(List<IlgiAlanlariParametreModel> ilgiAlanlari) {
		this.ilgiAlanlari = ilgiAlanlari;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<RolModel> getRoller() {
		return roller;
	}

	public void setRoller(List<RolModel> roller) {
		this.roller = roller;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getRolesAsString() {
		StringBuilder userRoles = new StringBuilder();
		for (RolModel role : getRoller()) {
			userRoles.append("'" + role.getRol() + "',");
		}
		userRoles.append("'NORMAUL_USER' ");
		return userRoles.toString();
	}
}
