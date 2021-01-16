package com.mesutemre.rol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mesutemre.exceptions.SavePointErrorException;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.rol.dao.IRolDao;
import com.mesutemre.rol.model.KullaniciRolModel;
import com.mesutemre.rol.model.RolModel;

@Service
public class RolService {

	@Autowired
	private IRolDao rolDao;
	
	public List<RolModel> getKullaniciRolListe(RolModel kriter){
		return rolDao.getKullaniciRolListe(kriter);
	}
	
	public ErrorDetail insertRol(RolModel model){
		return rolDao.insertRol(model);
	}
	
	@Transactional(rollbackFor = SavePointErrorException.class)
	public ErrorDetail kullaniciRollerKaydet(KullaniciRolModel model){
		return rolDao.kullaniciRolKaydet(model);
	}
	
}
