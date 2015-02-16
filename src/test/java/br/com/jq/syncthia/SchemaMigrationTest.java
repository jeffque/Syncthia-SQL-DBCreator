package br.com.jq.syncthia;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.sample.Sample1MigrationSchema;
import br.com.jq.syncthia.sample.Sample2MigrationsSchema;

/**
 * Unit test for simple App.
 */
public class SchemaMigrationTest extends TestCase {
	SchemaCollection collection;
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public SchemaMigrationTest(String testName) {
		super(testName);
		
		collection = new SchemaCollection();
		
		collection.registerSchema(new Sample1MigrationSchema());
		collection.registerSchema(new Sample2MigrationsSchema());
		collection.createOrMigrateSchema();
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(SchemaMigrationTest.class);
	}

	public void testCreateSchema() {
		List<SchemaCreator> schemaList = collection.getRegisteredSchemas();
		
		assertEquals(3, schemaList.size());
	}
	
	public void testTableSchemaVersion() {
		SchemaCreator schema1Migration = collection.getSchema("Sample 1 migration schema");
		SchemaCreator schema2Migrations = collection.getSchema("Sample 2 migrations schema");
		
		for (Table t: schema1Migration.getTables()) {
			assertEquals("v1", t.getSchemaVersion());
		}
		
		for (Table t: schema2Migrations.getTables()) {
			assertEquals("v1", t.getSchemaVersion());
		}
	}
	
	public void testTableDesiredVersion() {
		SchemaCreator schema1Migration = collection.getSchema("Sample 1 migration schema");
		SchemaCreator schema2Migrations = collection.getSchema("Sample 2 migrations schema");
		
		for (Table t: schema1Migration.getTables()) {
			assertEquals("v2", t.getDesiredVersion());
		}
		
		for (Table t: schema2Migrations.getTables()) {
			assertEquals("v3", t.getDesiredVersion());
		}
	}
}