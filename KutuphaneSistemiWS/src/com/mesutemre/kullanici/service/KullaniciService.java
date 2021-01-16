package com.mesutemre.kullanici.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mesutemre.exceptions.SavePointErrorException;
import com.mesutemre.kullanici.dao.IKullaniciDao;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.kullanici.model.KullaniciResim;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.parametre.service.ParametreService;
import com.mesutemre.util.KutuphaneSistemiUtil;

@Service
public class KullaniciService {

	 @Autowired
	 private IKullaniciDao kullaniciDao;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private ParametreService parametreService;
	
	public KullaniciModel getKullaniciByKullaniciAdi(String username){
		return kullaniciDao.getKullaniciBilgi(username);
	}
	
	@Transactional(rollbackFor = SavePointErrorException.class)
	public ErrorDetail kullaniciKaydet(KullaniciModel model){
		model.setPassword(passwordEncoder.encode(model.getPassword()));
		
		return kullaniciDao.kullaniciKaydet(model);
	}
	
	public List<KullaniciModel> kullaniciFiltrele(String username) {
		return kullaniciDao.kullaniciFiltrele(username);
	}
	
	@Transactional(rollbackFor = SavePointErrorException.class)
	public ErrorDetail kullaniciGuncelle(KullaniciModel model){
		parametreService.kullaniciIlgiAlanSil(model);
		parametreService.saveKullaniciIlgiAlanlar(model, model.getIlgiAlanlari());
		
		return kullaniciDao.kullaniciGuncelle(model);
	}
	
	public ErrorDetail kullaniciProfilResimYukle(MultipartFile file,String username) {
		ErrorDetail errorDetail = null;
		BufferedOutputStream stream = null;
		
		try {
			String uploadPath = KutuphaneSistemiUtil.getUploadProfilResmiPath()+username;
			File theDir = new File(uploadPath);
			if(!theDir.exists()) {
				theDir.mkdirs();
			}
			String extension = KutuphaneSistemiUtil.getFileExtension(file
					.getOriginalFilename());
			
			if(extension.equals("jpeg") || extension.equals("png") || extension.equals("jpg")) {
				File uploadedPersonelFile = new File(uploadPath+"/"+username+"."+extension);
				stream = new BufferedOutputStream(new FileOutputStream(uploadedPersonelFile));
				FileUtils.writeByteArrayToFile(uploadedPersonelFile, file.getBytes());
				//FileCopyUtils.copy(file.getBytes(), stream);
				byte[] resized = KutuphaneSistemiUtil.resizeImage(uploadedPersonelFile, 400, 400);
				FileCopyUtils.copy(resized, stream);
				kullaniciDao.kullaniciResimGuncelle(username);
			}else {
				errorDetail = new ErrorDetail(500, "Lütfen bir resim dosyası yükleyiniz!");
			}
		} catch (Exception e) {
			errorDetail = new ErrorDetail(500, e.getMessage());
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
		
		return errorDetail;
	}
	
	public KullaniciResim getKullaniciResimByKullaniciAdi(String kullaniciAdi) {
		KullaniciResim kullaniciResim = null;
		KullaniciModel kullaniciModel = getKullaniciByKullaniciAdi(kullaniciAdi);
		String resim = "";
		File resimFile;
		
		if(kullaniciModel.getResim() != null){
			final File folder = new File(KutuphaneSistemiUtil.getUploadProfilResmiPath()+kullaniciAdi);
			String absolutePath = null;
			for (final File fileEntry : folder.listFiles()) {
		        absolutePath = fileEntry.getAbsolutePath();
		    }
			resimFile = new File(absolutePath);
			resim = KutuphaneSistemiUtil.imageToBase64String(resimFile);
			kullaniciResim = new KullaniciResim();
			
			kullaniciResim.setUserImageBase64(resim);
			kullaniciResim.setUsername(kullaniciAdi);
		}else{
			resimFile = new File(KutuphaneSistemiUtil.getUploadProfilResmiPath()+"logo_xs.png");
			resim = KutuphaneSistemiUtil.imageToBase64String(resimFile);
			kullaniciResim = new KullaniciResim();
			
			kullaniciResim.setUserImageBase64(resim);
			kullaniciResim.setUsername(kullaniciAdi);
		}
		return kullaniciResim;
	}
	
	public KullaniciResim getKullaniciResim() {
		String kullaniciAdi = KutuphaneSistemiUtil.getLoggedInUser();
		return this.getKullaniciResimByKullaniciAdi(kullaniciAdi);
	}
}
