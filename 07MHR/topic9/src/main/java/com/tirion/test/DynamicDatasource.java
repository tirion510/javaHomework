package com.tirion.test;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDatasource extends AbstractRoutingDataSource {

	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

	public DynamicDatasource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return getDataSource();
	}

	public static void setDataSource(String dataSource) {
		CONTEXT_HOLDER.set(dataSource);
	}

	public static String getDataSource() {
		return CONTEXT_HOLDER.get();
	}

	public static void clearDataSource() {
		CONTEXT_HOLDER.remove();
	}

}
