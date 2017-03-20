package com.github.samuelbr.sleuth.datasource.extract;

import org.junit.Test;

public class SelectSpanNameExtractorTest extends AbstractSpanNameExtractorTest {

	public SelectSpanNameExtractorTest() {
		super(new SelectSpanNameExtractor());
	}
	
	@Test
	public void extractSpanName() {
		assertSpanName("s_employee", "SELECT * FROM employee");
		assertSpanName("s_employee.department", "SELECT * FROM employee, department");
		assertSpanName("s_TableA.TableB.TableC.TableD", "SELECT * FROM TableA a INNER JOIN TableB b on b.aID = a.aID INNER JOIN TableC c on c.cID = b.cID INNER JOIN TableD d on d.dID = a.dID");
		assertSpanName("s_Customers.Orders", "SELECT Customers.CustomerName, Orders.OrderID FROM Customers FULL OUTER JOIN Orders ON Customers.CustomerID=Orders.CustomerID ORDER BY Customers.CustomerName;");
	}
	
	@Test
	public void extractSpanNameFail() {
		assertSpanName(null, "UPDATE T SET C1 = C1 + 1");
		assertSpanName(null, "DELETE FROM pies");
	}	
	
}
