package com.example.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.example.dao")
public class PersistanceConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	
			LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
			entityManagerFactory.setDataSource(dataSource);
			entityManagerFactory.setPackagesToScan("com.example.entity");
			entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
			entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
			Properties additionalProperties = new Properties();
			additionalProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			additionalProperties.put("hibernate.show_sql", true);
			additionalProperties.put("hibernate.hbm2ddl.auto", "create");
			additionalProperties.put("hibernate.order_updates", "true");
			entityManagerFactory.setJpaProperties(additionalProperties);
	
			return entityManagerFactory;
		}
	
		@Bean
		public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
			JpaTransactionManager transactionManager = new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(emf);
			return transactionManager;
		}
}

