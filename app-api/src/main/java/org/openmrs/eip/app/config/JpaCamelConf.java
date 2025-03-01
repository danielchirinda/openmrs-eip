package org.openmrs.eip.app.config;

import javax.persistence.EntityManagerFactory;

import org.apache.camel.component.jpa.JpaComponent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class JpaCamelConf {
	
	private EntityManagerFactory entityManagerFactory;
	
	public JpaCamelConf(@Qualifier(value = "mngtEntityManager") final EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	@Bean(value = "jpa")
	public JpaComponent jpa() {
		JpaComponent comp = new JpaComponent();
		comp.setEntityManagerFactory(entityManagerFactory);
		
		return comp;
	}
}
