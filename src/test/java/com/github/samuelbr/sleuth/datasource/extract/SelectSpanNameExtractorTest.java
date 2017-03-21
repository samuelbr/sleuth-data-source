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
		assertSpanName("s_rs_relation_entity", "select relationen0_.id as id1_4_, relationen0_.obj_from as obj_from2_4_, relationen0_.obj_to as obj_to3_4_, relationen0_.technical_name as technica4_4_, relationen0_.tenant as tenant5_4_, relationen0_.valid_from as valid_fr6_4_, relationen0_.valid_to as valid_to7_4_ from rs_relation_entity relationen0_ where (relationen0_.technical_name in (? , ?)) and relationen0_.tenant=? and relationen0_.obj_from=? and relationen0_.obj_to=? and relationen0_.valid_from<=? and relationen0_.valid_to>=? limit ?");
	}
	
	@Test
	public void extractSpanNameFail() {
		assertSpanName(null, "UPDATE T SET C1 = C1 + 1");
		assertSpanName(null, "DELETE FROM pies");
	}	
	
}
