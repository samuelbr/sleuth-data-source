package com.github.samuelbr.sleuth.datasource.extract;

import org.junit.Test;

public class UpdateSpanNameExtractorTest extends AbstractSpanNameExtractorTest {

	public UpdateSpanNameExtractorTest() {
		super(new UpdateSpanNameExtractor());
	}
	
	@Test
	public void extractSpanName() {
		assertSpanName("u_T", "UPDATE T SET C1 = C1 + 1");
	}
	
	@Test
	public void extractSpanNameFail() {
		assertSpanName(null, "SELECT * FROM employee, department");
		assertSpanName(null, "SELECT * FROM TableA a INNER JOIN TableB b on b.aID = a.aID INNER JOIN TableC c on c.cID = b.cID INNER JOIN TableD d on d.dID = a.dID");
		assertSpanName(null, "SELECT Customers.CustomerName, Orders.OrderID FROM Customers FULL OUTER JOIN Orders ON Customers.CustomerID=Orders.CustomerID ORDER BY Customers.CustomerName;");		
	}

}
