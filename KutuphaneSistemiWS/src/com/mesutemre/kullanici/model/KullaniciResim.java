package com.mesutemre.kullanici.model;

import java.io.Serializable;

public class KullaniciResim implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String userImageBase64;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserImageBase64() {
		return userImageBase64;
	}

	public void setUserImageBase64(String userImageBase64) {
		this.userImageBase64 = userImageBase64;
	}

}
