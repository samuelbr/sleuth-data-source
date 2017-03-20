package com.github.samuelbr.sleuth.datasource.extract;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import net.ttddyy.dsproxy.QueryInfo;

abstract class AbstractSpanNameExtractorTest {

	protected QueryInfoExtractor extractor;
	
	public AbstractSpanNameExtractorTest(QueryInfoExtractor extractor) {
		this.extractor = extractor;
	}
	
	protected void assertSpanName(String expected, String sql) {
		List<QueryInfo> queryInfoList = Collections.singletonList(new QueryInfo(sql));
		String extractedSpanName = extractor.extractSpanName(queryInfoList);
		
		assertEquals(expected, extractedSpanName);
	}	
	
}
