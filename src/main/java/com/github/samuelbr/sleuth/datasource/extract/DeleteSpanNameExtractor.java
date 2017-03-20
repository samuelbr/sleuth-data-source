package com.github.samuelbr.sleuth.datasource.extract;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ttddyy.dsproxy.QueryInfo;

public class DeleteSpanNameExtractor implements QueryInfoExtractor {

	private static final Pattern DELETE = Pattern.compile("delete\\s+from\\s+(\\S+)", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	
	@Override
	public String extractSpanName(List<QueryInfo> queryInfoList) {
		if (queryInfoList.isEmpty()) {
			return null;
		}
		
		String query = queryInfoList.get(0).getQuery();
		
		Matcher deleteMatcher = DELETE.matcher(query);
		
		if (deleteMatcher.find()) {
			return "d_"+deleteMatcher.group(1);
		}
		
		return null;
	}

	@Override
	public Map<String, String> extractAdditionalFields(List<QueryInfo> queryInfoList) {
		return null;
	}

}
