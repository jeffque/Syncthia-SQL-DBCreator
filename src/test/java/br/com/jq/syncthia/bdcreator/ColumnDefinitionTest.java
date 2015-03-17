package br.com.jq.syncthia.bdcreator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.column.Collation;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;

/**
 * Unit test for simple App.
 */
public class ColumnDefinitionTest extends TestCase {
	SchemaCollection collection;
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public ColumnDefinitionTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ColumnDefinitionTest.class);
	}

	public void testNoSizeNoCollation() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		
		assertEquals("COL STRING", c.colDescription());
	}
	
	public void testSizeNoCollation() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setSize(15);
		
		assertEquals("COL STRING(15)", c.colDescription());
	}
	
	public void testNoSizeCollation() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		
		c.setCollation(Collation.BINARY);
		assertEquals("COL STRING COLLATE BINARY", c.colDescription());
		
		c.setCollation(Collation.NOCASE);
		assertEquals("COL STRING COLLATE NOCASE", c.colDescription());
		
		c.setCollation(Collation.RTRIM);
		assertEquals("COL STRING COLLATE RTRIM", c.colDescription());
	}
	
	public void testSizeCollation() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setSize(15);
		
		c.setCollation(Collation.BINARY);
		assertEquals("COL STRING(15) COLLATE BINARY", c.colDescription());
		
		c.setCollation(Collation.NOCASE);
		assertEquals("COL STRING(15) COLLATE NOCASE", c.colDescription());
		
		c.setCollation(Collation.RTRIM);
		assertEquals("COL STRING(15) COLLATE RTRIM", c.colDescription());
	}
	
	public void test1Precision() {
		Column c = new Column();
		c.setName("COL");
		c.setType("DOUBLE");
		c.setPrecision1(15);
		
		assertEquals("COL DOUBLE(15)", c.colDescription());
	}
	
	public void test2Precision() {
		Column c = new Column();
		c.setName("COL");
		c.setType("DOUBLE");
		c.setPrecision1(15);
		c.setPrecision2(10);
		
		assertEquals("COL DOUBLE(15,10)", c.colDescription());
	}
	
	// NULLABLE
	public void testNoSizeNoCollationNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setNullable(true);
		
		assertEquals("COL STRING NULL", c.colDescription());
	}
	
	public void testSizeNoCollationNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setSize(15);
		c.setNullable(true);
		
		assertEquals("COL STRING(15) NULL", c.colDescription());
	}
	
	public void testNoSizeCollationNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setNullable(true);
		
		c.setCollation(Collation.BINARY);
		assertEquals("COL STRING COLLATE BINARY NULL", c.colDescription());
		
		c.setCollation(Collation.NOCASE);
		assertEquals("COL STRING COLLATE NOCASE NULL", c.colDescription());
		
		c.setCollation(Collation.RTRIM);
		assertEquals("COL STRING COLLATE RTRIM NULL", c.colDescription());
	}
	
	public void testSizeCollationNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setSize(15);
		c.setNullable(true);
		
		c.setCollation(Collation.BINARY);
		assertEquals("COL STRING(15) COLLATE BINARY NULL", c.colDescription());
		
		c.setCollation(Collation.NOCASE);
		assertEquals("COL STRING(15) COLLATE NOCASE NULL", c.colDescription());
		
		c.setCollation(Collation.RTRIM);
		assertEquals("COL STRING(15) COLLATE RTRIM NULL", c.colDescription());
	}
	
	public void test1PrecisionNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("DOUBLE");
		c.setPrecision1(15);
		c.setNullable(true);
		
		assertEquals("COL DOUBLE(15) NULL", c.colDescription());
	}
	
	public void test2PrecisionNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("DOUBLE");
		c.setPrecision1(15);
		c.setPrecision2(10);
		c.setNullable(true);
		
		assertEquals("COL DOUBLE(15,10) NULL", c.colDescription());
	}
	
	// NOT NULLABLE
	public void testNoSizeNoCollationNotNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setNullable(false);
		
		assertEquals("COL STRING NOT NULL", c.colDescription());
	}
	
	public void testSizeNoCollationNotNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setSize(15);
		c.setNullable(false);
		
		assertEquals("COL STRING(15) NOT NULL", c.colDescription());
	}
	
	public void testNoSizeCollationNotNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setNullable(false);
		
		c.setCollation(Collation.BINARY);
		assertEquals("COL STRING COLLATE BINARY NOT NULL", c.colDescription());
		
		c.setCollation(Collation.NOCASE);
		assertEquals("COL STRING COLLATE NOCASE NOT NULL", c.colDescription());
		
		c.setCollation(Collation.RTRIM);
		assertEquals("COL STRING COLLATE RTRIM NOT NULL", c.colDescription());
	}
	
	public void testSizeCollationNotNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("STRING");
		c.setSize(15);
		c.setNullable(false);
		
		c.setCollation(Collation.BINARY);
		assertEquals("COL STRING(15) COLLATE BINARY NOT NULL", c.colDescription());
		
		c.setCollation(Collation.NOCASE);
		assertEquals("COL STRING(15) COLLATE NOCASE NOT NULL", c.colDescription());
		
		c.setCollation(Collation.RTRIM);
		assertEquals("COL STRING(15) COLLATE RTRIM NOT NULL", c.colDescription());
	}
	
	public void test1PrecisionNotNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("DOUBLE");
		c.setPrecision1(15);
		c.setNullable(false);
		
		assertEquals("COL DOUBLE(15) NOT NULL", c.colDescription());
	}
	
	public void test2PrecisionNotNullable() {
		Column c = new Column();
		c.setName("COL");
		c.setType("DOUBLE");
		c.setPrecision1(15);
		c.setPrecision2(10);
		c.setNullable(false);
		
		assertEquals("COL DOUBLE(15,10) NOT NULL", c.colDescription());
	}
	
	public void testAIPKColDescription() {
		ColumnAutoIncrement aipkCol = new ColumnAutoIncrement("AIPK");
		
		assertEquals("AIPK INTEGER PRIMARY KEY", aipkCol.colDescription());
	}
	
}
