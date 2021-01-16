package com.mesutemre.kitap.dao;

import java.util.List;

import com.mesutemre.kitap.model.KitapModel;
import com.mesutemre.kitap.model.KitapPuanModel;
import com.mesutemre.kitap.model.KitapTurIstatistikModel;
import com.mesutemre.kitap.model.KitapYorumModel;
import com.mesutemre.kullanici.model.KullaniciModel;

public interface IKitapDao {

	public List<KitapModel> 				getKitapListeByKriter(KitapModel kriter,KullaniciModel kullanici);
	public List<KitapTurIstatistikModel> 	getKitapTurIstatistik();
	public int								kitapKaydet(KitapModel model);
	public void								kitapResimUrlGuncelle(int kitapId,String url);
	public int								kitapPuanKaydet(KitapPuanModel model);
	public int								kitapPuanGuncelle(KitapPuanModel model);
	public int								kitapYorumKaydet(KitapYorumModel model);
	public int 								kitapYorumGuncelle(KitapYorumModel model);
	public List<KitapPuanModel>				getAdetliPuanListeByKitapId(int kitapId);
	public List<KitapYorumModel>			getKitapYorumlariByKitapId(int kitapId);
}
