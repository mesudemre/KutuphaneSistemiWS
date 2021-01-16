package com.mesutemre.sayfa.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mesutemre.model.ErrorDetail;
import com.mesutemre.sayfa.model.SayfaModel;
import com.mesutemre.sayfa.rowmapper.SayfaModelRowMapper;

/**
 * @author mesutemre.celenk
 *
 */
@Repository
public class SayfaDao implements ISayfaDao{

	private JdbcTemplate jdbctemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
//	@Cacheable(value="sayfaliste",unless="#result==null")
	public List<SayfaModel> getSayfaListe() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT ");
		sql.append(" s.id,s.sayfaad,s.angularroutename,s.status,s.olusturmatarihi, ");
		sql.append(" u.username,u.ad,u.soyad ");
		sql.append(" FROM ");
		sql.append(" kutuphanesistemi.sayfalar s ");
		sql.append(" JOIN kutuphanesistemi.users u ON s.olusturan=u.username ");
		
		List<SayfaModel> liste = jdbctemplate.query(sql.toString(), new SayfaModelRowMapper());
		
		return liste;
	}
	
	@Override
	public ErrorDetail saveSayfa(SayfaModel model) {
		ErrorDetail errorDetail = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append(" INSERT INTO ");
		sql.append(" kutuphanesistemi.sayfalar ");
		sql.append(" ( ");
		sql.append(" sayfaad,angularroutename,status,olusturmatarihi,olusturan ");
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append(" ?,?,?,CURDATE(),? ");
		sql.append(" ) ");
		
		int result = jdbctemplate.update(sql.toString(), new Object[]{
			model.getSayfaAd(),
			model.getAngularRouteName(),
			model.getDurum().getValue(),
			model.getOlusturan().getUsername()
		});
		
		if(result<1){
			errorDetail = new ErrorDetail(500, "Sayfa tanımlanırken hata meydana geldi!");
		}else{
			net.sf.ehcache.CacheManager.getInstance().getCache("sayfaliste").removeAll();
		}
		
		return errorDetail;
	}
	
	@Override
	public ErrorDetail updateSayfa(SayfaModel sayfaModel){
		ErrorDetail errorDetail = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE ");
		sql.append(" kutuphanesistemi.sayfalar ");
		sql.append(" SET sayfaad=?,angularroutename=?, ");
		sql.append(" status=? ");
		sql.append(" WHERE ");
		sql.append(" id=? ");
		
		int result = jdbctemplate.update(sql.toString(),new Object[]{
			sayfaModel.getSayfaAd(),
			sayfaModel.getAngularRouteName(),
			sayfaModel.getDurum().getValue(),
			sayfaModel.getId()
		});
		
		if(result<1){
			errorDetail = new ErrorDetail(500,"Sayfa güncellenirken hata meydana geldi!");
		}else{
			net.sf.ehcache.CacheManager.getInstance().getCache("sayfaliste").removeAll();
		}
		
		return errorDetail;
	}
}
