package br.com.jq.syncthia.bdcreator;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.sample.SampleSchema;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.table.Table;

/**
 * Unit test for simple App.
 */
public class CreateSchemaTest extends TestCase {
	SchemaCollection collection;
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public CreateSchemaTest(String testName) {
		super(testName);
		
		collection = new SchemaCollection();
		
		collection.registerSchema(new SampleSchema());
		
		try {
			collection.setConnection(DriverManager.getConnection("jdbc:sqlite:teste.db"));
			collection.createOrMigrateSchema();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(CreateSchemaTest.class);
	}

	public void testCreateSchema() {
		List<SchemaCreator> schemaList = collection.getRegisteredSchemas();
		
		assertEquals(2, schemaList.size()); // Basic Structural Scheme AND Sample schema
		
		assertNotNull(collection.getSchema("Basic Structural Scheme"));
		assertNotNull(collection.getSchema("Sample schema"));
		assertNull(collection.getSchema("Not Basic Structural Scheme"));
		assertNull(collection.getSchema("Not Sample schema"));
		
		assertEquals("Basic Structural Scheme", collection.getSchema("Basic Structural Scheme").getName());
		assertEquals("Sample schema", collection.getSchema("Sample schema").getName());
		
		assertEquals(collection.getSchema("Basic Structural Scheme"), schemaList.get(0));
		assertEquals(collection.getSchema("Sample schema"), schemaList.get(1));
	}
	
	public void testTableNames() {
		List<SchemaCreator> schemaList = collection.getRegisteredSchemas();
		SchemaCreator sampleSchema = schemaList.get(1);
		
		assertEquals(1, sampleSchema.getTables().size());
		
		assertNotNull(sampleSchema.getTable("SAMPLE_TABLE"));
		assertNull(sampleSchema.getTable("NOT_SAMPLE_TABLE"));
		
		assertEquals("SAMPLE_TABLE", sampleSchema.getTable("SAMPLE_TABLE").getName());
		assertEquals(sampleSchema.getTable("SAMPLE_TABLE"), sampleSchema.getTables().get(0));
	}
	
	public void testColumnNames() {
		Table sampleTable = collection.getRegisteredSchemas().get(1).getTables().get(0);
		List<Column> columnsSampleTable = sampleTable.getColumns();
		
		assertEquals(1, columnsSampleTable.size());
		
		assertNotNull(sampleTable.getColumn("AGE"));
		assertNull(sampleTable.getColumn("NOT_AGE"));
		
		assertEquals("AGE", sampleTable.getColumn("AGE").getName());
		assertEquals(sampleTable.getColumn("AGE"), columnsSampleTable.get(0));
	}
}
