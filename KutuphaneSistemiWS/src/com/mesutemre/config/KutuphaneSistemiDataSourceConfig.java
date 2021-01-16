package com.mesutemre.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author mesudemre
 * @version 1.0
 * @version 2.0 (10.05.2017)
 * @since <b style='color:#0D68FA'>19.02.2017</b> , <b style = 'color:orange'>KutuphaneSistemiDataSourceConfig class'ı 
 * 			create edildi ve dataSource() eklendi</b></br>
 * 	<b style='color:#0D68FA'>19.02.2017</b> , <b style = 'color:orange'>dataSource() JNDI ile kullanılmaya başlandı.
 * 				Bunun için obje üzeride değişiklikler yapıldı</b></br>
 */

@Configuration
@EnableTransactionManagement
public class KutuphaneSistemiDataSourceConfig {

    @Autowired
    private Environment env;
    
    @Bean
    public DataSource dataSource(){
	
	DriverManagerDataSource  ds = new DriverManagerDataSource();
	ds.setDriverClassName(env.getProperty("kutuphanesistemi.datasource.driver"));
	ds.setUrl(env.getProperty("kutuphanesistemi.datasource.url"));
	ds.setUsername(env.getProperty("kutuphanesistemi.datasource.username"));
	ds.setPassword(env.getProperty("kutuphanesistemi.datasource.password"));
	
	return ds;
	
//	final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
//        dsLookup.setResourceRef(true);
//        DataSource dataSource = dsLookup.getDataSource("jdbc/kutuphanesistemi");
//        return dataSource;
    }
	
    @Bean
	public DataSourceTransactionManager getTransactionManager() {
       DataSourceTransactionManager txManager = new DataSourceTransactionManager();
 
       DataSource dataSource = this.dataSource();
       txManager.setDataSource(dataSource);
 
       return txManager;
	}
    
}
