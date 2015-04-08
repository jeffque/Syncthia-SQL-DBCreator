package br.com.jq.syncthia.bdcreator;

import java.sql.DriverManager;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.sample.SampleSchemaNewVersionAddColumn;
import br.com.jq.syncthia.bdcreator.sample.SampleSchemaOldVersion;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;

/**
 * Unit test for simple App.
 */
//TODO this test is broken!
public class SchemaMigrationAutoMigrationTest extends TestCase {
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public SchemaMigrationAutoMigrationTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(SchemaMigrationAutoMigrationTest.class);
	}

	public void testAutoMigrationAddCollumn() throws SQLException {
		SchemaCreator testedSchema = new SampleSchemaOldVersion();
		SchemaCollection collection = new SchemaCollection();
		collection.registerSchema(testedSchema);
		collection.setConnection(DriverManager.getConnection("jdbc:sqlite:teste.db"));
		
		collection.createOrMigrateSchema();
		
		collection.removeRegisteredSchema(testedSchema);
		testedSchema = new SampleSchemaNewVersionAddColumn();
		collection.registerSchema(testedSchema);
		
		collection.createOrMigrateSchema();
		
		SampleSchemaNewVersionAddColumn.SampleTableXEntity newEntity = new SampleSchemaNewVersionAddColumn.SampleTableXEntity();
		
		newEntity.setA("Value");
		newEntity.setB("Another Value");
		newEntity.persistEntity(collection);
		
		testedSchema.dropUnit();
	}
}
