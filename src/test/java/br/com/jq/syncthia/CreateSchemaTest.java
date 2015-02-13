package br.com.jq.syncthia;

import java.util.List;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.sample.SampleSchema;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class CreateSchemaTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public CreateSchemaTest(String testName) {
		super(testName);
		
		SchemaCreator.registerSchema(new SampleSchema());
		SchemaCreator.createOrMigrateSchema();
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(CreateSchemaTest.class);
	}

	public void testCreateSchema() {
		List<SchemaCreator> schemaList = SchemaCreator.getRegisteredSchemas();
		assertEquals(2, schemaList.size());
		assertEquals("Basic Structural Scheme", schemaList.get(0).getSchemaName());
		assertEquals("Sample schema", schemaList.get(1).getSchemaName());
	}
	
	public void testTableNames() {
		List<SchemaCreator> schemaList = SchemaCreator.getRegisteredSchemas();
		assertEquals("SAMPLE_TABLE", schemaList.get(1).getTables().get(0).getName());
	}
}
