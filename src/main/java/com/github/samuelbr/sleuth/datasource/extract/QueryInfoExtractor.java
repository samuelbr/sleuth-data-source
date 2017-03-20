package com.github.samuelbr.sleuth.datasource.extract;

import java.util.List;
import java.util.Map;

import net.ttddyy.dsproxy.QueryInfo;

public interface QueryInfoExtractor {

	String extractSpanName(List<QueryInfo> queryInfoList);
	
	Map<String, String> extractAdditionalFields(List<QueryInfo> queryInfoList);
	
}
