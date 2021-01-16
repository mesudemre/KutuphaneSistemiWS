package com.mesutemre.sayfa.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.mesutemre.enums.ParametreDurumEnum;
import com.mesutemre.model.BaseModel;

public class SayfaModel extends BaseModel {

	private int id;
	
	@NotEmpty(message = "Sayfa adı boş olamaz!")
	@Size(min = 2,max = 50 , message = "Sayfa adı en az 3 en fazla 50 karakter olabilir")
	private String sayfaAd;
	
	@NotEmpty(message = "Angular Route Name boş olamaz!")
	private String angularRouteName;
	private ParametreDurumEnum durum;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSayfaAd() {
		return sayfaAd;
	}

	public void setSayfaAd(String sayfaAd) {
		this.sayfaAd = sayfaAd;
	}

	public String getAngularRouteName() {
		return angularRouteName;
	}

	public void setAngularRouteName(String angularRouteName) {
		this.angularRouteName = angularRouteName;
	}

	public ParametreDurumEnum getDurum() {
		return durum;
	}

	public void setDurum(ParametreDurumEnum durum) {
		this.durum = durum;
	}

}
