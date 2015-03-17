package br.com.jq.syncthia.bdcreator.sample;

import br.com.jq.syncthia.bdcreator.column.Collation;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;

public class SampleTableNocase extends Table {
	public SampleTableNocase() {
		setName("NOCASE_TABLE");
		
		addColumn(new ColumnAutoIncrement("PK"));
		Column name = new Column();
		name.setName("NAME");
		name.setType("STRING");
		name.setCollation(Collation.NOCASE);
		name.setNullable(false);
		
		addColumn(name);
		
		TableKey nameKey = new TableKey(KeyType.UNIQUE_KEY);
		nameKey.setName("UNIQUE_NAME_CONSTRAINT");
		nameKey.addColumn(name);
		
		addKey(nameKey);
	}
}
