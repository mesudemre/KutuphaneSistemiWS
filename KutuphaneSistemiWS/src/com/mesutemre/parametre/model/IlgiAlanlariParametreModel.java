package com.mesutemre.parametre.model;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mesutemre.enums.ParametreDurumEnum;
import com.mesutemre.model.BaseModel;

/**
 * @author mesutemre.celenk
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class IlgiAlanlariParametreModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	@NotEmpty(message = "Açıklama boş olamaz!")
	@Size(min = 3  , message = "Açıklama alanı en az 3 karakter olmalı!")
	private String aciklama;
	
	private ParametreDurumEnum durum;
	
	private String resim;
	
	public IlgiAlanlariParametreModel(int id,String aciklama) {
		this.id = id;
		this.aciklama = aciklama;
	}
	
	public IlgiAlanlariParametreModel() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public ParametreDurumEnum getDurum() {
		return durum;
	}

	public void setDurum(ParametreDurumEnum durum) {
		this.durum = durum;
	}

	public String getResim() {
		return resim;
	}

	public void setResim(String resim) {
		this.resim = resim;
	}
}
