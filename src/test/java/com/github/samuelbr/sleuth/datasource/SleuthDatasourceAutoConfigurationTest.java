package com.github.samuelbr.sleuth.datasource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Test;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration;
import org.springframework.cloud.sleuth.log.SleuthLogAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.samuelbr.sleuth.datasource.autoconfig.SleuthDatasourceAutoConfiguration;

import net.ttddyy.dsproxy.support.ProxyDataSource;

public class SleuthDatasourceAutoConfigurationTest {

	private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	
	@After
	public void close() {
		context.close();
	}
	
	@Test
	public void testDataSource() {
		context.register(PropertyPlaceholderAutoConfiguration.class,
				TraceAutoConfiguration.class,
				SleuthLogAutoConfiguration.class,
				DataSourceAutoConfiguration.class,
				SleuthDatasourceAutoConfiguration.class);
		
		context.refresh();
		
		DataSource bean = context.getBean(DataSource.class);
		
		assertNotNull(bean);
		
		assertThat(bean, instanceOf(ProxyDataSource.class));
	}
	
}
