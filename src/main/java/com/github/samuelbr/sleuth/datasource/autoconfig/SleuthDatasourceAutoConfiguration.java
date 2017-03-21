package com.github.samuelbr.sleuth.datasource.autoconfig;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.samuelbr.sleuth.datasource.listener.TracerQueryExecutionListener;

import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

@Configuration
@ConditionalOnProperty(value="sleuth.datasource.enabled", matchIfMissing=true)
@ConditionalOnClass(value=net.ttddyy.dsproxy.support.ProxyDataSource.class)
@EnableConfigurationProperties({SleuthDatasourceProperties.class})
@AutoConfigureBefore(value=DataSourceAutoConfiguration.class)
public class SleuthDatasourceAutoConfiguration {
	
	@Bean
	public DataSource dataSource(Tracer tracer, DataSourceProperties properties) {
		DataSource dataSource = properties.initializeDataSourceBuilder().build();
		
		return ProxyDataSourceBuilder
				.create(dataSource)
				.listener(new TracerQueryExecutionListener(tracer))
				.build();		
	}
	
}
