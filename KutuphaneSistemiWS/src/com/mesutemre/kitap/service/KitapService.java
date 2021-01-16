package com.mesutemre.kitap.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mesutemre.enums.KitapIslemEnum;
import com.mesutemre.kitap.dao.IKitapDao;
import com.mesutemre.kitap.model.KitapModel;
import com.mesutemre.kitap.model.KitapPuanModel;
import com.mesutemre.kitap.model.KitapTurIstatistikModel;
import com.mesutemre.kitap.model.KitapYorumModel;
import com.mesutemre.kitap.model.YorumListeModel;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.kullanici.service.KullaniciService;
import com.mesutemre.log.dao.ILogDao;
import com.mesutemre.log.model.LogModel;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.model.ResponseStatus;
import com.mesutemre.util.KutuphaneSistemiUtil;

@Service
public class KitapService {

	@Autowired
	private IKitapDao kitapDao;
	
	@Autowired
	private ILogDao logDao;
	
	@Autowired
	private KullaniciService kullaniciService;
	
	@Autowired
	private Environment env;
	
	public List<KitapModel> getKitapModelListeByKriter(KitapModel kriter){
		KullaniciModel kullanici = kullaniciService.getKullaniciByKullaniciAdi(KutuphaneSistemiUtil.getLoggedInUser());
		return kitapDao.getKitapListeByKriter(kriter,kullanici);
	}
	
	public List<KitapTurIstatistikModel> getKitapTurIstatistik(){
		return kitapDao.getKitapTurIstatistik();
	}
	
	public ResponseStatus kitapKaydet(KitapModel model) {
		ResponseStatus responseStatus = new ResponseStatus();
		KullaniciModel kullanici = kullaniciService.getKullaniciByKullaniciAdi(KutuphaneSistemiUtil.getLoggedInUser());
		
		if(model.getId()>0) {
			//TODO : Bu kısımda güncelleme yapılacak
		}else {
			int kitapId = kitapDao.kitapKaydet(model);
			if(kitapId>0) {
				LogModel logModel = new LogModel(KitapIslemEnum.S, kullanici, kitapId);
				ErrorDetail ed = logDao.logKaydet(logModel);
				if(ed == null) {
					responseStatus.setStatusCode("200");
					responseStatus.setStatusMessage(""+kitapId);
				}else {
					responseStatus.setStatusCode("500");
					responseStatus.setStatusMessage(ed.getStatusMessage());
				}
			}else {
				responseStatus.setStatusCode("500");
				responseStatus.setStatusMessage("Kitap kaydı yapılırken hata meydana geldi!");
			}
		}
		
		return responseStatus;
	}
	
	public void kitapResimGuncelle(int kitapId) {
		
	}
	
	public ResponseStatus kitapResimYukle(MultipartFile file,String kitapId) {
		ResponseStatus responseStatus = new ResponseStatus();
		BufferedOutputStream stream = null;
		
		try {
			String uploadPath = KutuphaneSistemiUtil.getKitapPath()+kitapId;
			File theDir = new File(uploadPath);
			if(!theDir.exists()) {
				theDir.mkdirs();
			}
			String extension = KutuphaneSistemiUtil.getFileExtension(file
					.getOriginalFilename());
			if(extension.equals("jpeg") || extension.equals("png") || extension.equals("jpg")) {
				File uploadedPersonelFile = new File(uploadPath+"/"+kitapId+"."+extension);
				stream = new BufferedOutputStream(new FileOutputStream(uploadedPersonelFile));
				FileUtils.writeByteArrayToFile(uploadedPersonelFile, file.getBytes());
				byte[] resized = KutuphaneSistemiUtil.resizeImage(uploadedPersonelFile, 200, 300);
				FileCopyUtils.copy(resized, stream);
				
				String url = env.getProperty("kutuphanesistemi.resim.contextPath")+kitapId;
				kitapDao.kitapResimUrlGuncelle(Integer.parseInt(kitapId), url);
				
				responseStatus.setStatusCode("200");
				responseStatus.setStatusMessage("Resim başarıyla yüklendi");
			}else {
				responseStatus.setStatusCode("500");
				responseStatus.setStatusMessage("Lütfen bir resim dosyası yükleyiniz!");
			}
		} catch (Exception e) {
			responseStatus.setStatusCode("500");
			responseStatus.setStatusMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return responseStatus;
	}
	
	public ResponseStatus kitapPuanKaydet(KitapPuanModel model) {
		ResponseStatus responseStatus = new ResponseStatus();
		KullaniciModel kullanici = kullaniciService.getKullaniciByKullaniciAdi(KutuphaneSistemiUtil.getLoggedInUser());
		
		if(model.getKitap().getKitapPuan()>0) {
			model.setGuncelleyen(kullanici);
			int guncellemeSonuc = kitapDao.kitapPuanGuncelle(model);
			if(guncellemeSonuc<1) {
				responseStatus.setStatusCode("500");
				responseStatus.setStatusMessage("Kitap puanı kaydedilemedi!");
				
				return responseStatus;
			}
			
			responseStatus.setStatusCode("200");
			responseStatus.setStatusMessage("Kitap puanı başarıyla kaydedildi");
		}else {
			model.setOlusturan(kullanici);
			int kayitSonuc = kitapDao.kitapPuanKaydet(model);
			if(kayitSonuc<1) {
				responseStatus.setStatusCode("500");
				responseStatus.setStatusMessage("Kitap puanı kaydedilemedi!");
				
				return responseStatus;
			}
			
			responseStatus.setStatusCode("200");
			responseStatus.setStatusMessage("Kitap puanı başarıyla kaydedildi");
		}
		
		return responseStatus;
	}
	
	public ResponseStatus kitapYorumKaydet(KitapYorumModel model) {
		ResponseStatus responseStatus = new ResponseStatus();
		
		if(model.getId()>0) {
			int guncellemeSonuc = kitapDao.kitapYorumGuncelle(model);
			if(guncellemeSonuc<1) {
				if(model.getStatus() != 1) {
					responseStatus.setStatusCode("500");
					responseStatus.setStatusMessage("Yorumunuz silinirken hata meydana geldi!");
				}else {
					responseStatus.setStatusCode("500");
					responseStatus.setStatusMessage("Yorumunuz güncellenirken hata meydana geldi!");
				}
			}else {
				if(model.getStatus() != 1) {
					responseStatus.setStatusCode("200");
					responseStatus.setStatusMessage("Yorumunuz başarıyla silindi");
				}else {
					responseStatus.setStatusCode("200");
					responseStatus.setStatusMessage("Yorumunuz başarıyla güncellendi");
				}
			}
			return responseStatus;
		}else {
			KullaniciModel kullanici = kullaniciService.getKullaniciByKullaniciAdi(KutuphaneSistemiUtil.getLoggedInUser());
			model.setOlusturan(kullanici);
			int kayitSonuc = kitapDao.kitapYorumKaydet(model);
			if(kayitSonuc<1) {
				responseStatus.setStatusCode("500");
				responseStatus.setStatusMessage("Yorumunuz kaydedilirken hata meydana geldi!");
				
				return responseStatus;
			}
			responseStatus.setStatusCode("200");
			responseStatus.setStatusMessage("Yorumunuz başarıyla kaydedildi");
		}
		
		return responseStatus;
	}
	
	public YorumListeModel getYorumListeByKitapId(int kitapId){
		YorumListeModel model = new YorumListeModel();
		
		List<KitapPuanModel> puanListe = kitapDao.getAdetliPuanListeByKitapId(kitapId);
		if(puanListe == null || puanListe.size() == 0) {
			puanListe = new ArrayList<KitapPuanModel>();
			puanListe.add(new KitapPuanModel(1, 0));
			puanListe.add(new KitapPuanModel(2, 0));
			puanListe.add(new KitapPuanModel(3, 0));
			puanListe.add(new KitapPuanModel(4, 0));
			puanListe.add(new KitapPuanModel(5, 0));
		}else if(puanListe != null && puanListe.size()<5){
			for(int i=1;i<6;i++) {
				if(!puanListedeMevcut(i, puanListe)) {
					puanListe.add(new KitapPuanModel(i, 0));
				}
			}
		}
		model.setPuanListe(puanListe);
		
		List<KitapYorumModel> yorumListe = kitapDao.getKitapYorumlariByKitapId(kitapId);
		model.setYorumListe(yorumListe);
		
		return model;
	}
	
	private boolean puanListedeMevcut(int puan,List<KitapPuanModel> liste) {
		for (int i = 0; i < liste.size(); i++) {
			if(liste.get(i).getPuan() == puan) {
				return true;
			}
		}
		return false;
	}
	
}
