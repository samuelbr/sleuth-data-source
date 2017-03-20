package com.github.samuelbr.sleuth.datasource.listener;

import java.util.List;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;

import com.github.samuelbr.sleuth.datasource.extract.DefaultQueryInfoExtractor;
import com.github.samuelbr.sleuth.datasource.extract.QueryInfoExtractor;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;

public class TracerQueryExecutionListener implements QueryExecutionListener {

	private final Tracer tracer;
	
	private ThreadLocal<Span> spans = new ThreadLocal<>();
	
	private QueryInfoExtractor extractor = new DefaultQueryInfoExtractor();
	
	public TracerQueryExecutionListener(Tracer tracer) {
		this.tracer = tracer;
	}

	@Override
	public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
		String spanName = extractor.extractSpanName(queryInfoList);
		
		Span newSpan = tracer.createSpan(spanName);
		
		tracer.addTag(Span.SPAN_LOCAL_COMPONENT_TAG_NAME, "Datasource");
		tracer.addTag("Method", execInfo.getMethod().getName());
		tracer.addTag("StatementType", execInfo.getStatementType().name());
		tracer.addTag("Batch", Boolean.toString(execInfo.isBatch()));
		tracer.addTag("DataSourceName", execInfo.getDataSourceName());
		tracer.addTag("BatchSize", Integer.toString(execInfo.getBatchSize()));
		
		extractor.extractAdditionalFields(queryInfoList).entrySet().stream()
			.forEach((e) -> tracer.addTag(e.getKey(), e.getValue()));
		
		spans.set(newSpan);
	}

	@Override
	public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
		Span span = spans.get();
		spans.remove();
		
		tracer.close(span);
	}

}
