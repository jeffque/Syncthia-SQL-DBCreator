package br.com.jq.syncthia.bdcreator;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.sample.Sample1MigrationSchema;
import br.com.jq.syncthia.bdcreator.sample.Sample2MigrationsSchema;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

/**
 * Unit test for simple App.
 */
public class SchemaMigrationWithConnectionTest extends TestCase {
	SchemaCollection collection;
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public SchemaMigrationWithConnectionTest(String testName) {
		super(testName);
		
		collection = new SchemaCollection();
		
		collection.registerSchema(new Sample1MigrationSchema());
		collection.registerSchema(new Sample2MigrationsSchema());
		
		try {
			collection.setConnection(DriverManager.getConnection("jdbc:sqlite:teste.db"));
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
		return new TestSuite(SchemaMigrationWithConnectionTest.class);
	}

	public void testCreateSchema() {
		List<SchemaCreator> schemaList = collection.getRegisteredSchemas();
		
		assertEquals(3, schemaList.size());
	}
	
	public void testTableSchemaVersion() {
		SchemaCreator schema1Migration = collection.getSchema("Sample 1 migration schema");
		SchemaCreator schema2Migrations = collection.getSchema("Sample 2 migrations schema");
		
		// Migration has already run, so t.getRegisteredVersion() and t.getDesiredVersion() should be equal
		for (Table t: schema1Migration.getTables()) {
			assertEquals(t.getDesiredVersion(), t.getRegisteredVersion());
		}
		
		for (Table t: schema2Migrations.getTables()) {
			assertEquals(t.getDesiredVersion(), t.getRegisteredVersion());
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
	
	public void testTableMigrations() {
		SchemaCreator schema1Migration = collection.getSchema("Sample 1 migration schema");
		SchemaCreator schema2Migrations = collection.getSchema("Sample 2 migrations schema");
		
		// Migration has already run, so no migrations are needed
		for (Table t: schema1Migration.getTables()) {
			List<MigrationStrategy> migrations = t.getDesiredMigrations();
			
			assertEquals(0, migrations.size());
		}
		
		// Migration has already run, so no migrations are needed
		for (Table t: schema2Migrations.getTables()) {
			List<MigrationStrategy> migrations = t.getDesiredMigrations();
			
			assertEquals(0, migrations.size());
		}
	}
}
