package com.mesutemre.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.model.ResponseStatus;
import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;
import com.mesutemre.parametre.model.YayineviModel;
import com.mesutemre.parametre.service.ParametreService;
import com.mesutemre.sayfa.model.SayfaModel;
import com.mesutemre.util.KutuphaneSistemiUtil;

/**
 * @author mesutemre.celenk
 *
 */
@RestController
@RequestMapping("/api/parametre/")
public class ParametreController {
	
	@Autowired
	private ParametreService parametreService;

	@RequestMapping(value = "/kitaptur/liste",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<IlgiAlanlariParametreModel>> getKitapTurleri(@RequestBody IlgiAlanlariParametreModel kriter){
		List<IlgiAlanlariParametreModel> liste = parametreService.ilgiAlanListe(kriter);
		
		if(liste == null || liste.size() == 0){
			return new ResponseEntity<List<IlgiAlanlariParametreModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<IlgiAlanlariParametreModel>>(liste,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/kitaptur/liste",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<IlgiAlanlariParametreModel>> getKitapTurleri(){
		List<IlgiAlanlariParametreModel> liste = parametreService.ilgiAlanListe(null);
		
		if(liste == null || liste.size() == 0){
			return new ResponseEntity<List<IlgiAlanlariParametreModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<IlgiAlanlariParametreModel>>(liste,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/kitapturler",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<IlgiAlanlariParametreModel>> getKitapTurListe(){
		List<IlgiAlanlariParametreModel> liste = parametreService.ilgiAlanListe(null);
		
		if(liste == null || liste.size() == 0){
			return new ResponseEntity<List<IlgiAlanlariParametreModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<IlgiAlanlariParametreModel>>(liste,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/kitaptur/kaydet" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> saveKitapTur(@Valid @RequestBody IlgiAlanlariParametreModel model/*,BindingResult results*/){
		String username = KutuphaneSistemiUtil.getLoggedInUser();
		
		if(username == null){
			return new ResponseEntity<ResponseStatus>(new ResponseStatus("401", "Lütfen sisteme giriş yapınız!"),HttpStatus.UNAUTHORIZED);
		}
		if(model.getId()<1){
			model.setOlusturan(new KullaniciModel(username));
		}else{
			model.setGuncelleyen(new KullaniciModel(username));
		}
		
		/*
		IlgiAlanlariValidator validator = new IlgiAlanlariValidator();
		validator.validate(model,results);
		
		if(results.hasErrors()){
			return new ResponseEntity<ResponseStatus>(new ResponseStatus("400",results.getAllErrors().get(0).getDefaultMessage()),HttpStatus.BAD_REQUEST);
		}
		*/
		
		ErrorDetail errorDetail = parametreService.saveIlgiAlanParametre(model);
		
		if(errorDetail == null && model.getId()>0){
			return new ResponseEntity<ResponseStatus>(new ResponseStatus("200", "Parametre başarıyla güncellendi"),HttpStatus.OK);
		}
		
		if(errorDetail == null && model.getId() == 0){
			return new ResponseEntity<ResponseStatus>(new ResponseStatus("200", "Parametre başarıyla kaydedildi"),HttpStatus.OK);
		}
		
		return new ResponseEntity<ResponseStatus>(new ResponseStatus(String.valueOf(errorDetail.getStatusCode()), errorDetail.getStatusMessage()),HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/sayfa/liste" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<SayfaModel>> getSayfaListe(){
		List<SayfaModel> liste = parametreService.getSayfaListe();
		
		if(liste == null || liste.isEmpty()){
			return new ResponseEntity<List<SayfaModel>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<SayfaModel>>(liste,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sayfa/kaydet" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> saveSayfa(@Valid @RequestBody SayfaModel model){
		ErrorDetail errorDetail = null;
		String username = KutuphaneSistemiUtil.getLoggedInUser();
		if(username == null){
			return new ResponseEntity<ResponseStatus>(new ResponseStatus(HttpStatus.UNAUTHORIZED.toString(), "Lütfen giriş yapınız!"),HttpStatus.UNAUTHORIZED);
		}
		model.setOlusturan(new KullaniciModel(username));
		errorDetail = parametreService.saveSayfa(model);
		
		if(errorDetail != null){
			return new ResponseEntity<ResponseStatus>(new ResponseStatus(HttpStatus.BAD_REQUEST.toString(),"Sayfa kaydedilirken hata meydana geldi!"),HttpStatus.BAD_REQUEST);
		}
		
		if(model.getId()>0){
			return new ResponseEntity<ResponseStatus>(new ResponseStatus(HttpStatus.OK.toString(),"Sayfa başarıyla güncellendi."),HttpStatus.OK);
		}
		
		return new ResponseEntity<ResponseStatus>(new ResponseStatus(HttpStatus.OK.toString(),"Sayfa başarıyla kaydedildi."),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/yayinevi/liste" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<YayineviModel>> getYayinEviListe(){
		List<YayineviModel> liste = parametreService.getYayinEviModelListe();
		
		if(liste == null || liste.isEmpty()){
			return new ResponseEntity<List<YayineviModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<YayineviModel>>(liste,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/yayinevi/kaydet" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> yayinEviParamKaydet(@Valid @RequestBody YayineviModel model){
		ErrorDetail errorDetail = parametreService.yayinEviKaydet(model);
		ResponseStatus responseStatus = new ResponseStatus();
		
		if(errorDetail != null){
			responseStatus.setStatusMessage(errorDetail.getStatusMessage());
			responseStatus.setStatusCode("500");
			
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		responseStatus.setStatusCode("200");
		responseStatus.setStatusMessage("Yayınevi başarıyla kaydedildi");
		if(model.getId()>0)
			responseStatus.setStatusMessage("Yayınevi başarıyla güncellendi");
			
		return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ilgialan/resim/{ilgiAlanId}" , method = RequestMethod.GET ,produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getKullaniciResim(@PathVariable(value = "ilgiAlanId") String ilgiAlanId) throws IOException {
		File file = new File(KutuphaneSistemiUtil.getKutuphaneSistemiPath()+"icons/"+ilgiAlanId+".png");
		InputStream in = null;
		if(file.exists()) {
			in = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			in.close();
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(in),HttpStatus.OK);
		}
		file = new File(KutuphaneSistemiUtil.getKutuphaneSistemiPath()+"not_found_image.png");
		in = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
		in.close();
		
		return new ResponseEntity<byte[]>(IOUtils.toByteArray(in),HttpStatus.NOT_FOUND);
	}

}
