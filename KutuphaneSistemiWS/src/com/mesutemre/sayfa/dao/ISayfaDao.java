package com.mesutemre.sayfa.dao;

import java.util.List;

import com.mesutemre.model.ErrorDetail;
import com.mesutemre.sayfa.model.SayfaModel;

public interface ISayfaDao {

	public List<SayfaModel> getSayfaListe();
	public ErrorDetail saveSayfa(SayfaModel model);
	public ErrorDetail updateSayfa(SayfaModel sayfaModel);
	
}
