package br.com.jq.syncthia;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.sample.SampleTableAIPK;

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
}
