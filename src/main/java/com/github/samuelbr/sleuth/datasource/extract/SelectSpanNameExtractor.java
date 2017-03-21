package com.github.samuelbr.sleuth.datasource.extract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ttddyy.dsproxy.QueryInfo;

public class SelectSpanNameExtractor implements QueryInfoExtractor {

	private static final Pattern SELECT = Pattern.compile("select.*\\s+from\\s+(.*?)(where\\s|group\\s|having\\s|window\\s|union\\s|intersect\\s|except\\s|order\\s|limit\\s|offset\\s|fetch\\s|$).*", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	private static final Pattern TABLES_PATTERN = Pattern.compile("(\\s+JOIN\\s+(\\S+)|(,\\s+(\\S+)))", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);	
	private static final Pattern FIRST_TABLE_PATTERN = Pattern.compile("^([^\\s,]+)", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	
	@Override
	public String extractSpanName(List<QueryInfo> queryInfoList) {
		if (queryInfoList.isEmpty()) {
			return null;
		}
		
		String query = queryInfoList.get(0).getQuery();
		Matcher matcher = SELECT.matcher(query);
		if (matcher.matches()) {
			return extractSpanNameFrom(matcher.group(1));
		}
		
		return null;
	}

	@Override
	public Map<String, String> extractAdditionalFields(List<QueryInfo> queryInfoList) {
		return null;
	}
	
	String extractSpanNameFrom(String from) {
		List<String> tables = new ArrayList<>();
		from = from.trim();
		
		Matcher firstTableMatcher = FIRST_TABLE_PATTERN.matcher(from);
		if (firstTableMatcher.find()) {
			tables.add(firstTableMatcher.group(0));
		}
		
		Matcher tablesMatcher = TABLES_PATTERN.matcher(from);
		while(tablesMatcher.find()) {
			String table = Optional
					.ofNullable(tablesMatcher.group(2))
					.orElse(tablesMatcher.group(4));
			tables.add(table);
		}
		
		if (tables.isEmpty()) {
			return null;
		}
		
		return "s_"+String.join(".", tables);
	}
	
}