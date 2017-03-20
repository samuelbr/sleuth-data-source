package com.github.samuelbr.sleuth.datasource.extract;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ttddyy.dsproxy.QueryInfo;

public class UpdateSpanNameExtractor implements QueryInfoExtractor {

	private static final Pattern UPDATE = Pattern.compile("update\\s+(\\S+)", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	
	@Override
	public String extractSpanName(List<QueryInfo> queryInfoList) {
		if (queryInfoList.isEmpty()) {
			return null;
		}
		
		String query = queryInfoList.get(0).getQuery();
		
		Matcher updateMatcher = UPDATE.matcher(query);
		
		if (updateMatcher.find()) {
			return "u_"+updateMatcher.group(1);
		}
		
		return null;
	}

	@Override
	public Map<String, String> extractAdditionalFields(List<QueryInfo> queryInfoList) {
		return null;
	}

}
