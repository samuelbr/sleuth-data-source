package com.github.samuelbr.sleuth.datasource.extract;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ttddyy.dsproxy.QueryInfo;

public class InsertSpanNameExtractor implements QueryInfoExtractor {

	private static final Pattern INSERT = Pattern.compile("insert\\s+into\\s+(\\S+)", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	
	@Override
	public String extractSpanName(List<QueryInfo> queryInfoList) {
		if (queryInfoList.isEmpty()) {
			return null;
		}
		
		String query = queryInfoList.get(0).getQuery();
		
		Matcher updateMatcher = INSERT.matcher(query);
		
		if (updateMatcher.find()) {
			return "i_"+updateMatcher.group(1);
		}
		
		return null;
	}

	@Override
	public Map<String, String> extractAdditionalFields(List<QueryInfo> queryInfoList) {
		return null;
	}

}
