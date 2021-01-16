package com.mesutemre.parametre.dao;

import java.util.List;

import com.mesutemre.kullanici.model.KullaniciModel;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;
import com.mesutemre.parametre.model.YayineviModel;

public interface IParametreDao {

	public List<IlgiAlanlariParametreModel> ilgiAlanListe(IlgiAlanlariParametreModel kriter);
	public List<IlgiAlanlariParametreModel> getKullaniciIlgiAlanlari(String username);
	public ErrorDetail 						saveIlgiAlani(IlgiAlanlariParametreModel ilgiAlan);
	public ErrorDetail 						updateIlgiAlani(IlgiAlanlariParametreModel ilgiAlan);
	public ErrorDetail						saveKullaniciIlgiAlanlari(KullaniciModel kullanici,List<IlgiAlanlariParametreModel> ilgiAlanListe);
	public ErrorDetail						saveYayinEviParam(YayineviModel model);
	public List<YayineviModel>				getYayinEviListe();
	public ErrorDetail						updateYayinEviParam(YayineviModel model);
	public void 							kullaniciilgiAlanSil(String username);
}
