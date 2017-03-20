package com.github.samuelbr.sleuth.datasource.extract;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ttddyy.dsproxy.QueryInfo;

public class ExtraSpanNameExtractor implements QueryInfoExtractor {

	private static final Pattern EXTRA = Pattern.compile("^(\\S+)\\s+(\\S+)\\s+([\\w\\d\\._]+)", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	
	@Override
	public String extractSpanName(List<QueryInfo> queryInfoList) {
		if (queryInfoList.isEmpty()) {
			return null;
		}
		
		String query = queryInfoList.get(0).getQuery();
		
		Matcher extraMatcher = EXTRA.matcher(query);
		
		if (extraMatcher.find()) {
			return "x_"+String.join(".", extraMatcher.group(1), extraMatcher.group(2), extraMatcher.group(3));
		}
		
		return null;
	}

	@Override
	public Map<String, String> extractAdditionalFields(List<QueryInfo> queryInfoList) {
		return null;
	}

}
