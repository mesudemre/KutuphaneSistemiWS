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

import com.mesutemre.kitap.model.KitapModel;
import com.mesutemre.kitap.model.KitapPuanModel;
import com.mesutemre.kitap.model.KitapTurIstatistikModel;
import com.mesutemre.kitap.model.KitapYorumModel;
import com.mesutemre.kitap.model.YorumListeModel;
import com.mesutemre.kitap.service.KitapService;
import com.mesutemre.model.ResponseStatus;
import com.mesutemre.util.KutuphaneSistemiUtil;

@RestController
@RequestMapping("/api/kitap")
public class KitapController {
	
	@Autowired
	private KitapService kitapService;

	@RequestMapping(value = "/liste" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<KitapModel>> getKitapListe(@RequestBody KitapModel kriter){
		List<KitapModel> liste = kitapService.getKitapModelListeByKriter(kriter);
		
		if(liste == null || liste.size() == 0){
			return new ResponseEntity<List<KitapModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<KitapModel>>(liste,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tur/istatistik" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<KitapTurIstatistikModel>> getKitapTurDashListe(){
		List<KitapTurIstatistikModel> liste = kitapService.getKitapTurIstatistik();
		
		if(liste == null || liste.size() == 0){
			return new ResponseEntity<List<KitapTurIstatistikModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<KitapTurIstatistikModel>>(liste,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resim/{resimId}" , method = RequestMethod.GET ,produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getKitapResim(@PathVariable(value = "resimId") String resimId) throws IOException {
		File file = new File(KutuphaneSistemiUtil.getKitapPath()+resimId+"/"+resimId+".jpg");
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
	
	@RequestMapping(value = "/kaydet" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> kitapKaydet(@RequestBody KitapModel model){
		ResponseStatus responseStatus = kitapService.kitapKaydet(model);
		if(responseStatus.getStatusCode().equals("500")) {
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resim/yukle" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> kitapResimYukle(@RequestParam("file") MultipartFile file,
			  											  @RequestParam("kitapId") String kitapId){
		ResponseStatus responseStatus = null;
		
		if(kitapId == null) {
			responseStatus = new ResponseStatus("400", "Kitap boş geçilemez!");
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.BAD_REQUEST);
		}
		if(file.isEmpty()) {
			responseStatus = new ResponseStatus("404", "Yüklenecek dosya eksik!");
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.NOT_FOUND);
			
		}
		
		responseStatus = kitapService.kitapResimYukle(file, kitapId);
		
		if(!responseStatus.getStatusCode().equals("200")) {
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/puan/kaydet",method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> kitapPuanKaydet(@RequestBody KitapPuanModel model){
		ResponseStatus responseStatus = kitapService.kitapPuanKaydet(model);
		
		if(!"200".equals(responseStatus.getStatusCode())){
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/yorum/kaydet",method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseStatus> kitapYorumKaydet(@RequestBody KitapYorumModel model){
		ResponseStatus responseStatus = kitapService.kitapYorumKaydet(model);
		
		if(!"200".equals(responseStatus.getStatusCode())){
			return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseStatus>(responseStatus,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/yorumlar/{kitapId}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<YorumListeModel> getYorumListeByKitapId(@PathVariable("kitapId") String kitapId){
		YorumListeModel model = kitapService.getYorumListeByKitapId(Integer.parseInt(kitapId));
		return new ResponseEntity<YorumListeModel>(model,HttpStatus.OK);
	}
	
}
