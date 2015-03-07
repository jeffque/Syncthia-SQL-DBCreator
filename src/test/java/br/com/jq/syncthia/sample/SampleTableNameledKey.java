package br.com.jq.syncthia.sample;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;

public class SampleTableNameledKey extends Table {
	public SampleTableNameledKey() {
		Column c = new Column();
		c.setName("PK");
		
		addColumn(c);
		
		TableKey pk = new TableKey(KeyType.PRIMARY_KEY);
		pk.addColumn(c);
		pk.setName("NAMED_KEY");
		
		addKey(pk);
	}
}
