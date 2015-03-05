package br.com.jq.syncthia;

import java.sql.*;
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
		
		collection.getSchema("Sample schema").dropSchema();
		
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rsRegisteredSchema = stmt.executeQuery("SELECT * FROM REGISTERED_SCHEMAS WHERE SCHEMA_NAME ='Sample schema'");
			assertFalse(rsRegisteredSchema.next());
			rsRegisteredSchema.close();
			
			ResultSet rsMigratables = stmt.executeQuery("SELECT * FROM MIGRATABLE_VERSION WHERE MIGRATABLE_SCHEMA_VERSION ='Sample schema'");
			assertFalse(rsMigratables.next());
			rsMigratables.close();

			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			eThrowed = e;
			e.printStackTrace();
		};
		
		assertNull("There should be no throwed excpetion", eThrowed);
	}
}
