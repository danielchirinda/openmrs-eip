package org.openmrs.eip.app.config;

import static java.util.Collections.singletonMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.openmrs.eip.app.SyncConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "openmrsEntityManager", transactionManagerRef = "openmrsTransactionManager", basePackages = {
        "org.openmrs.eip.component.repository" })
public class OpenmrsDataSourceConfig {
	
	private static final Logger log = LoggerFactory.getLogger(OpenmrsDataSourceConfig.class);
	
	@Value("${spring.openmrs-datasource.dialect}")
	private String hibernateDialect;
	
	@Primary
	@Bean(name = "openmrsDataSource")
	@ConfigurationProperties(prefix = "spring.openmrs-datasource")
	public DataSource dataSource() {
		HikariDataSource ds = ((HikariDataSource) DataSourceBuilder.create().build());
		ds.setPoolName(SyncConstants.DEFAULT_OPENMRS_POOL_NAME);
		ds.setMaximumPoolSize(SyncConstants.DEFAULT_CONN_POOL_SIZE);
		return ds;
	}
	
	@Primary
	@Bean(name = "openmrsEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManager(final EntityManagerFactoryBuilder builder,
	                                                            @Qualifier("openmrsDataSource") final DataSource dataSource) {
		return builder.dataSource(dataSource).packages("org.openmrs.eip.component.entity").persistenceUnit("openmrs")
		        .properties(singletonMap("hibernate.dialect", hibernateDialect)).build();
	}
	
	@Primary
	@Bean(name = "openmrsTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("openmrsEntityManager") final EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
}
