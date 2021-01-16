package com.mesutemre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mesutemre.model.ErrorDetail;
import com.mesutemre.model.ResponseStatus;
import com.mesutemre.rol.model.KullaniciRolModel;
import com.mesutemre.rol.model.RolModel;
import com.mesutemre.rol.service.RolService;

@RestController
@RequestMapping("/api/rol/")
public class RolController {

	@Autowired
	private RolService rolService;
	
	@RequestMapping(value = "/kullanici/liste" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<RolModel>> getRolListe(@RequestBody RolModel kriter){
		List<RolModel> rolListe = rolService.getKullaniciRolListe(kriter);
		
		if(rolListe == null || rolListe.isEmpty()){
			return new ResponseEntity<List<RolModel>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<RolModel>>(rolListe,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/kullanicilar/kaydet" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> saveKullaniciRolListe(@RequestBody KullaniciRolModel kullaniciRolModel){
		ResponseStatus responseStatus = new ResponseStatus();
		ErrorDetail errorDetail = rolService.kullaniciRollerKaydet(kullaniciRolModel);
		
		if(errorDetail != null){
			responseStatus.setStatusCode("500");
			responseStatus.setStatusMessage(errorDetail.getStatusMessage());
			
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.BAD_REQUEST);
		}
		
		responseStatus.setStatusCode("200");
		responseStatus.setStatusMessage("Roller başarıyla kullanıcıların üstüne kaydedildi");
		
		return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.OK);
	}
	
}
