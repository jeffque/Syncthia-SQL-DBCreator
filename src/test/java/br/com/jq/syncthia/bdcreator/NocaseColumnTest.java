package br.com.jq.syncthia.bdcreator;

import java.sql.DriverManager;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.exceptions.CantPersistAutomaticException;
import br.com.jq.syncthia.bdcreator.sample.SampleEntityNocase;
import br.com.jq.syncthia.bdcreator.sample.SampleTableNocase;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;

/**
 * Unit test for simple App.
 */
public class NocaseColumnTest extends TestCase {
	SchemaCollection collection;
	SchemaCreator nocaseSchema;
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public NocaseColumnTest(String testName) {
		super(testName);
		nocaseSchema = new SchemaCreator() {
			
			@Override
			public String getName() {
				return "NOCASE SCHEMA";
			}
			
			@Override
			protected void schemaDefinition() {
				addTable(new SampleTableNocase());
			}
		};
		
		collection = new SchemaCollection();
		
		collection.registerSchema(nocaseSchema);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(NocaseColumnTest.class);
	}

	public void testCreateSchema() throws SQLException, CantPersistAutomaticException {
		collection.setConnection(DriverManager.getConnection("jdbc:sqlite:teste.db"));
		collection.createOrMigrateSchema();
		SampleEntityNocase entity1 = new SampleEntityNocase();
		entity1.setName("TESTE");
		entity1.persistEntity(collection);
		
		SampleEntityNocase entity2 = new SampleEntityNocase();
		entity2.setName("teste");
		entity2.autoSetAIPKCol(collection);
		
		assertEquals(entity1.getPk(), entity2.getPk());
		
		nocaseSchema.dropUnit();
	}
	
}
