package com.dao;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entitymangaer",transactionManagerRef = "transactionManager", basePackages = "com.dto")
public class DbConfig {
	
	@Bean(name = "employeeinfo")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource datasourceMaster () {
		return DataSourceBuilder.create().build();
	}
	 

	@Bean(name = "entityManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(EntityManagerFactoryBuilder builder,@Qualifier("employeeinfo") DataSource dataSource) {
		return  builder.dataSource(dataSource).packages(EmployeeDAOImpl.class).build(); 
	}

	@Bean(name ="transactionManager")
	@Primary
	public PlatformTransactionManager  masterManager(@Qualifier("entityManager") EntityManagerFactory entityManagerFactory)
	{
		return new JpaTransactionManager(entityManagerFactory);
	}

}
