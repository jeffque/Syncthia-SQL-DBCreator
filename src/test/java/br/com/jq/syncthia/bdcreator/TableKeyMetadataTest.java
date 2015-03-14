package br.com.jq.syncthia.bdcreator;

import java.sql.DriverManager;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.sample.*;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.table.Table;

/**
 * Unit test for simple App.
 */
public class TableKeyMetadataTest extends TestCase {
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public TableKeyMetadataTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TableKeyMetadataTest.class);
	}

	public void testAIPK() {
		Table t = new SampleTableAIPK();
		
		TableKey k = t.getPrimaryKey();
		
		assertEquals(1, k.getColumns().size());
		assertEquals(t.getColumn("PK"), k.getColumn("PK"));
		assertEquals("PRIMARY_KEY_CONSTRAINT", k.getName());
		assertEquals("", t.listKeyMetadata(new StringBuilder()).toString());
	}
	
	public void testNamelessPK() {
		Table t = new SampleTableNamelessKey();
		
		TableKey k = t.getPrimaryKey();
		
		assertEquals(1, k.getColumns().size());
		assertEquals(t.getColumn("PK"), k.getColumn("PK"));
		assertNull(k.getName());
		assertEquals("\t,\n\t" + k.keyDescription() + "\n", t.listKeyMetadata(new StringBuilder()).toString());
	}
	
	public void testNamedPK() {
		Table t = new SampleTableNamedKey();
		
		TableKey k = t.getPrimaryKey();
		
		assertEquals(1, k.getColumns().size());
		assertEquals(t.getColumn("PK"), k.getColumn("PK"));
		assertEquals("NAMED_KEY", k.getName());
		assertEquals("\t,\n\t" + k.keyDescription() + "\n", t.listKeyMetadata(new StringBuilder()).toString());
	}
	
	public void testArtificialAIPKSettet() throws SQLException {
		SchemaCollection schemaCollection = new SchemaCollection();
		SchemaCreator schemaCreator = new SchemaCreator() {
			
			@Override
			public String getName() {
				return "Sample Artificial AIPK Test";
			}
			
			@Override
			protected void schemaDefinition() {
				addTable(new SampleTableArtificialAIPK());
				
			}
		};
		
		schemaCollection.registerSchema(schemaCreator);
		schemaCollection.setConnection(DriverManager.getConnection("jdbc:sqlite:teste.db"));
		
		try {
			schemaCreator.dropUnit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// I think I can ignore case it ain't possible to drop the table in the schema...
			e.printStackTrace();
		}
		schemaCollection.createOrMigrateSchema();
		
		SampleTableArtificialAIPKEntity entity = new SampleTableArtificialAIPKEntity();
		entity.setName("TEST 1");
		entity.persistEntity(schemaCollection);
		assertEquals(1, entity.getPk().intValue());
		
		entity.setName("TEST 2");
		entity.persistEntity(schemaCollection);
		assertEquals(2, entity.getPk().intValue());
	}
}
