package com.github.samuelbr.sleuth.datasource.extract;

import org.junit.Test;

public class InsertSpanNameExtractorTest extends AbstractSpanNameExtractorTest {

	public InsertSpanNameExtractorTest() {
		super(new InsertSpanNameExtractor());
	}
	
	@Test
	public void extractSpanName() {
		assertSpanName("i_Customers", "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');");
	}
	
	@Test
	public void extractSpanNameFail() {
		assertSpanName(null, "SELECT * FROM employee, department");
		assertSpanName(null, "SELECT * FROM TableA a INNER JOIN TableB b on b.aID = a.aID INNER JOIN TableC c on c.cID = b.cID INNER JOIN TableD d on d.dID = a.dID");
		assertSpanName(null, "SELECT Customers.CustomerName, Orders.OrderID FROM Customers FULL OUTER JOIN Orders ON Customers.CustomerID=Orders.CustomerID ORDER BY Customers.CustomerName;");
		assertSpanName(null, "UPDATE T SET C1 = C1 + 1");
		assertSpanName(null, "DELETE FROM pies");
	}

}
