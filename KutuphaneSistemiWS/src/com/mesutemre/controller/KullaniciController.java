package com.mesutemre.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mesutemre.exceptions.UserNotFoundException;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.kullanici.model.KullaniciResim;
import com.mesutemre.kullanici.service.KullaniciService;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.model.ResponseStatus;
import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;
import com.mesutemre.parametre.service.ParametreService;
import com.mesutemre.rol.model.RolModel;
import com.mesutemre.rol.service.RolService;
import com.mesutemre.util.KutuphaneSistemiUtil;

/**
 * @author mesutemre.celenk
 *
 */
@RestController
@RequestMapping("/api/kullanici/")
public class KullaniciController {

	@Autowired
	private KullaniciService kullaniciService;
	
	@Autowired
	private ParametreService parametreService;
	
	@Autowired
	private RolService rolService;
	
	@RequestMapping(value = "/bilgi",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<KullaniciModel> getKullaniciBilgi(){
		KullaniciModel kullanici = kullaniciService.getKullaniciByKullaniciAdi(KutuphaneSistemiUtil.getLoggedInUser());
		
		if(kullanici == null){
			return new ResponseEntity<KullaniciModel>(HttpStatus.NOT_FOUND);
		}
		RolModel rolModel = new RolModel();
		rolModel.setRolKullanici(kullanici);
		kullanici.setRoller(rolService.getKullaniciRolListe(rolModel));
		
		return new ResponseEntity<KullaniciModel>(kullanici,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ilgialanlari",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<IlgiAlanlariParametreModel>> getUserIlgiAlanlari(@RequestBody KullaniciModel kullanici){
		KullaniciModel user = kullaniciService.getKullaniciByKullaniciAdi(kullanici.getUsername());
		if(user == null){
			throw new UserNotFoundException("Kullanıcı Bulunamadı");
		}
		List<IlgiAlanlariParametreModel> liste = parametreService.getKullaniciIlgiAlanlari(kullanici.getUsername());
		
		if(liste == null || liste.size() == 0){
			return new ResponseEntity<List<IlgiAlanlariParametreModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<IlgiAlanlariParametreModel>>(liste,HttpStatus.OK);
	}

	@RequestMapping(value = "/userprofil/{userName}" , method = RequestMethod.GET ,produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getKullaniciProfilResim(@PathVariable(value = "userName") String userName) throws IOException {
		File file = new File(KutuphaneSistemiUtil.getUploadProfilResmiPath()+userName+"/"+userName+".jpg");
		InputStream in = null;
		if(file.exists()) {
			in = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			in.close();
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(in),HttpStatus.OK);
		}
		file = new File(KutuphaneSistemiUtil.getUploadProfilResmiPath()+"logo_xs.png");
		in = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
		in.close();
		
		return new ResponseEntity<byte[]>(IOUtils.toByteArray(in),HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/kaydet" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> kullaniciKaydet(@RequestBody KullaniciModel model){
		ResponseStatus responseStatus = new ResponseStatus();
		ErrorDetail errorDetail = kullaniciService.kullaniciKaydet(model);
		
		if(errorDetail != null){
			responseStatus.setStatusCode("400");
			responseStatus.setStatusMessage(errorDetail.getStatusMessage());
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.BAD_REQUEST);
		}
		
		responseStatus.setStatusCode("200");
		responseStatus.setStatusMessage("Kayıt işlemi başarılı");
		return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtrele" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<KullaniciModel>> filterKullanici(@RequestParam String username){
		List<KullaniciModel> filteredKullanici = kullaniciService.kullaniciFiltrele(username);
		
		if(filteredKullanici != null && filteredKullanici.size()>0){
			return new ResponseEntity<List<KullaniciModel>>(filteredKullanici,HttpStatus.OK);
		}
		
		return new ResponseEntity<List<KullaniciModel>>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/guncelle" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> kullaniciGuncelle(@RequestBody KullaniciModel model){
		ResponseStatus responseStatus = null;
		ErrorDetail errorDetail = kullaniciService.kullaniciGuncelle(model);
		
		if(errorDetail != null){
			responseStatus = new ResponseStatus("500", errorDetail.getStatusMessage());
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.BAD_REQUEST);
		}
		
		responseStatus = new ResponseStatus("200", "Kullanıcı bilgileri başrıyla güncellendi");
		
		return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resim/yukle",method = RequestMethod.POST)
	public ResponseEntity<ResponseStatus> kullaniciResimYukle(@RequestParam("file") MultipartFile file,
															  @RequestParam("username") String username){
		ResponseStatus responseStatus = null;
		
		if(username == null) {
			responseStatus = new ResponseStatus("400", "Kullanıcı adı boş geçilemez!");
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.BAD_REQUEST);
		}
		if(file.isEmpty()) {
			responseStatus = new ResponseStatus("404", "Yüklenecek dosya eksik!");
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.NOT_FOUND);
			
		}
		
		ErrorDetail errorDetail = kullaniciService.kullaniciProfilResimYukle(file, username);
		
		if(errorDetail != null) {
			responseStatus = new ResponseStatus("500", errorDetail.getStatusMessage());
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		responseStatus = new ResponseStatus("200", "Resim başarıyla yüklendi");
		
		return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.OK);
	}
	
}
