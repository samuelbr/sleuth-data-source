package com.github.samuelbr.sleuth.datasource.extract;

import org.junit.Test;

public class ExtraSpanNameExtractorTest extends AbstractSpanNameExtractorTest {

	public ExtraSpanNameExtractorTest() {
		super(new ExtraSpanNameExtractor());
	}
	
	@Test
	public void extractSpanName() {
		assertSpanName("x_DROP.DATABASE.testDB", "DROP DATABASE testDB;");
		assertSpanName("x_CREATE.INDEX.idx_lastname", "CREATE INDEX idx_lastname ON Persons (LastName);");
	}

}
