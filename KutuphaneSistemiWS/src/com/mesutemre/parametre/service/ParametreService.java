package com.mesutemre.parametre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesutemre.enums.ParametreDurumEnum;
import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.kullanici.service.KullaniciService;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.parametre.dao.IParametreDao;
import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;
import com.mesutemre.parametre.model.YayineviModel;
import com.mesutemre.sayfa.dao.ISayfaDao;
import com.mesutemre.sayfa.model.SayfaModel;
import com.mesutemre.util.KutuphaneSistemiUtil;

/**
 * @author mesutemre.celenk
 *
 */
@Service
public class ParametreService {

	@Autowired
	private IParametreDao parametreDao;
	
	@Autowired
	private ISayfaDao     sayfaDao;
	
	@Autowired
	private KullaniciService kullaniciService;
	
	public List<IlgiAlanlariParametreModel> ilgiAlanListe(IlgiAlanlariParametreModel kriter) {
		return parametreDao.ilgiAlanListe(kriter);
	}
	
	public List<IlgiAlanlariParametreModel> getKullaniciIlgiAlanlari(String username) {
		return parametreDao.getKullaniciIlgiAlanlari(username);
	}
	
	public ErrorDetail saveIlgiAlanParametre(IlgiAlanlariParametreModel model){
		if(model.getId() == 0){
			return parametreDao.saveIlgiAlani(model);
		}else{
			return parametreDao.updateIlgiAlani(model);
		}
	}
	
	public ErrorDetail saveKullaniciIlgiAlanlar(KullaniciModel kullanici,List<IlgiAlanlariParametreModel> liste){
		return parametreDao.saveKullaniciIlgiAlanlari(kullanici, liste);
	}
	
	
	public List<SayfaModel> getSayfaListe(){
		return sayfaDao.getSayfaListe();
	}
	
	public ErrorDetail saveSayfa(SayfaModel model){
		ErrorDetail errorDetail = null;
		if(model.getId()>0){
			errorDetail = sayfaDao.updateSayfa(model);
		}else{
			model.setDurum(ParametreDurumEnum.AKTIF);
			errorDetail = sayfaDao.saveSayfa(model);
		}
		return errorDetail;
	}
	
	public List<YayineviModel> getYayinEviModelListe(){
		return parametreDao.getYayinEviListe();
	}
	
	public ErrorDetail yayinEviKaydet(YayineviModel model){
		ErrorDetail errorDetail = null;
		
		if(model.getId()>0){
			errorDetail = parametreDao.updateYayinEviParam(model);
		}else{
			KullaniciModel olusturan = kullaniciService.getKullaniciByKullaniciAdi(KutuphaneSistemiUtil.getLoggedInUser());
			model.setOlusturan(olusturan);
			model.setDurum(ParametreDurumEnum.AKTIF);
			
			errorDetail = parametreDao.saveYayinEviParam(model);
		}
		
		return errorDetail;
	}
	
	public void kullaniciIlgiAlanSil(KullaniciModel model){
		parametreDao.kullaniciilgiAlanSil(model.getUsername());
	}
}
