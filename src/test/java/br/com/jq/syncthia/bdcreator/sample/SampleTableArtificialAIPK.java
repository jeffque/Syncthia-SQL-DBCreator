package br.com.jq.syncthia.bdcreator.sample;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;

public class SampleTableArtificialAIPK extends Table {
	public SampleTableArtificialAIPK() {
		setName("SAMPLE_ARTIFICIAL_AIPK");
		addColumn(new ColumnAutoIncrement("PK"));
		Column name = new Column();
		name.setName("NAME");
		name.setType("STRING");
		name.setNullable(false);
		
		addColumn(name);
		
		TableKey nameKey = new TableKey(KeyType.UNIQUE_KEY);
		nameKey.setName("UNIQUE_NAME");
		nameKey.addColumn(name);
		
		addKey(nameKey);
	}
}
