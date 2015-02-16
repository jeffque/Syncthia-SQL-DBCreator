package br.com.jq.syncthia.bdcreator.schema.basicSchema;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;

public class TableVersion extends Table {
	public TableVersion() {
		setName("TABLE_VERSION");
		
		Column pkCol = new Column();
		Column tableName = new Column();
		Column tableSchemaVersion = new Column();
		
		pkCol.setName("PK_TABLE_VERSION");
		pkCol.setType("INT");
		pkCol.setNullable(false);
		
		tableName.setName("TABLE_NAME");
		tableName.setType("STRING");
		tableName.setNullable(false);
		
		tableSchemaVersion.setName("TABLE_SCHEMA_VERSION");
		tableSchemaVersion.setType("STRING");
		tableSchemaVersion.setNullable(false);
		
		addColumn(pkCol);
		addColumn(tableName);
		addColumn(tableSchemaVersion);
		
		TableKey pk = new TableKey(KeyType.PRIMARY_KEY);
		pk.setName("PRIMARY_KEY_CONSTRAINT");
		pk.addColumn(pkCol);
		
		TableKey uniqueName = new TableKey(KeyType.UNIQUE_KEY);
		uniqueName.setName("TABLE_NAME_CONSTRAINT");
		uniqueName.addColumn(tableName);
		
		addKey(pk);
		addKey(uniqueName);
	}
}
