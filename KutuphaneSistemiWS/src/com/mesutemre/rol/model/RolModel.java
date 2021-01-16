package com.mesutemre.rol.model;

import org.springframework.security.core.GrantedAuthority;

import com.mesutemre.enums.RoleTurEnum;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.model.BaseModel;

public class RolModel extends BaseModel implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private KullaniciModel rolKullanici;
	private RoleTurEnum rol;
	
	public RolModel() {
	}
	
	public RolModel(RoleTurEnum rol) {
		this.rol = rol;
	}
	
	public RolModel(int id) {
		this.id = id;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public KullaniciModel getRolKullanici() {
		return rolKullanici;
	}

	public void setRolKullanici(KullaniciModel rolKullanici) {
		this.rolKullanici = rolKullanici;
	}

	public RoleTurEnum getRol() {
		return rol;
	}

	public void setRol(RoleTurEnum rol) {
		this.rol = rol;
	}
	
	@Override
	public String getAuthority() {
		return rol.getValue();
	}

}
