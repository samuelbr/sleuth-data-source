package com.github.samuelbr.sleuth.datasource.extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import net.ttddyy.dsproxy.QueryInfo;

public class DefaultQueryInfoExtractor implements QueryInfoExtractor {

	private final List<QueryInfoExtractor> EXTRACTORS = new ArrayList<>();
	
	public DefaultQueryInfoExtractor() {
		EXTRACTORS.add(new SelectSpanNameExtractor());
		EXTRACTORS.add(new UpdateSpanNameExtractor());
		EXTRACTORS.add(new InsertSpanNameExtractor());
		EXTRACTORS.add(new DeleteSpanNameExtractor());
		EXTRACTORS.add(new ExtraSpanNameExtractor());
	}
	
	@Override
	public String extractSpanName(List<QueryInfo> queryInfoList) {
		if (queryInfoList.isEmpty()) {
			return "missingQuery";
		}
		
		Optional<String> spanName = EXTRACTORS.stream()
			.map((e) -> e.extractSpanName(queryInfoList))
			.filter(Objects::nonNull)
			.findFirst();

		return spanName.orElse("unknownQuery");
	}

	@Override
	public Map<String, String> extractAdditionalFields(List<QueryInfo> queryInfoList) {
		Map<String, String> result = new HashMap<>();

		result.put("QueriesCount", Integer.toString(queryInfoList.size()));
		
		if (!queryInfoList.isEmpty()) {
			QueryInfo queryInfo = queryInfoList.get(0);
			result.put("MainQuery", queryInfo.getQuery());
			
			String mainQueryParams = queryInfo.getParametersList().stream()
				.flatMap(List::stream)
				.map((s) -> s.getArgs()[1])
				.map(Objects::toString)
				.collect(Collectors.joining(","));
			
			result.put("MainQueryParams", mainQueryParams);
		}
		
		for (int i=1; i<queryInfoList.size(); i++) {
			result.put("Query"+i, queryInfoList.get(i).getQuery());
		}
		
		return result;
	}
	
}