package br.com.jq.syncthia.bdcreator;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.sample.*;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.table.Table;

/**
 * Unit test for simple App.
 */
public class ForeignKeyTest extends TestCase {
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public ForeignKeyTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ForeignKeyTest.class);
	}
	
	public void testFKStringFormat() throws SQLException {
		SchemaCollection schemaCollection = new SchemaCollection();
		SchemaCreator schemaCreator = new SampleForeignKey();
		schemaCollection.registerSchema(schemaCreator);
		schemaCollection.createOrMigrateSchema();
		
		Table parentTable = schemaCreator.getTable("TABLE_PARENT");
		Table childTable = schemaCreator.getTable("TABLE_CHILD");
		
		assertEquals("\n", parentTable.listForeignKeyMetadata(new StringBuilder()).toString());
		assertEquals("\t,\n\t FOREIGN KEY (FK_COL) REFERENCES TABLE_PARENT\n", childTable.listForeignKeyMetadata(new StringBuilder()).toString());
	}
	
	public void testFKSimpleInsert() throws SQLException {
		SchemaCollection schemaCollection = new SchemaCollection();
		SchemaCreator schemaCreator = new SampleForeignKey();
		schemaCollection.registerSchema(schemaCreator);
		
		SQLiteConfig config = new SQLiteConfig();
		config.enforceForeignKeys(true);
		config.setSharedCache(true);
		config.enableRecursiveTriggers(true);
		
		schemaCollection.setConnection(DriverManager.getConnection("jdbc:sqlite:teste.db", config.toProperties()));
		
		Table parentTable = schemaCreator.getTable("TABLE_PARENT");
		Table childTable = schemaCreator.getTable("TABLE_CHILD");
		try {
			childTable.dropUnit();
			parentTable.dropUnit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// I think I can ignore case it ain't possible to drop the table in the schema...
			e.printStackTrace();
		}
		schemaCollection.createOrMigrateSchema();
		
		PreparedStatement childInsertStmt = childTable.prepareInsertStatement(childTable.getOrdinaryColumns());
		childInsertStmt.setInt(1, 1);
		
		PreparedStatement parentInsertStmt = parentTable.prepareInsertStatement(parentTable.getOrdinaryColumns());
		parentInsertStmt.setString(1, "um");
		
		Exception oute = null;
		try {
			childInsertStmt.executeUpdate();
		} catch (SQLException e) {
			oute = e;
		}
		assertNotNull(oute);
		
		oute = null;
		try {
			parentInsertStmt.executeUpdate();
		} catch (SQLException e) {
			oute = e;
		}
		assertNull(oute);
		
		//XXX I don't know why, but this is necessary to insert stuff when the first statement failed
		childInsertStmt = childTable.prepareInsertStatement(childTable.getOrdinaryColumns());
		childInsertStmt.setInt(1, 1);
		oute = null;
		try {
			childInsertStmt.executeUpdate();
		} catch (SQLException e) {
			oute = e;
		}
		assertNull(oute);
	}
}
