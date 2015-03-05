package br.com.jq.syncthia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.sample.SampleSchema;

/**
 * Unit test for simple App.
 */
public class DropSchemaTest extends TestCase {
	SchemaCollection collection;
	Connection conn;
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public DropSchemaTest(String testName) {
		super(testName);
		
		collection = new SchemaCollection();
		
		collection.registerSchema(new SampleSchema());
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:teste.db");
			collection.setConnection(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		collection.createOrMigrateSchema();
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DropSchemaTest.class);
	}

	public void testDropSchema() {
		Exception eThrowed = null;
		List<SchemaCreator> schemaList = collection.getRegisteredSchemas();
		
		assertEquals(2, schemaList.size()); // Basic Structural Scheme AND Sample schema
		
		collection.getSchema("Basic Structural Scheme").dropSchema();
		
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("erro de sintaxe");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			eThrowed = e;
			e.printStackTrace();
		};
		
		assertNull("There should be no throwed excpetion", eThrowed);
	}
}
