package br.com.jq.syncthia.bdcreator.schema.basicSchema;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;

public class ViewVersion extends Table {
	public ViewVersion() {
		setName("VIEW_VERSION");
		
		Column pkCol = new Column();
		Column tableName = new Column();
		Column tableSchemaVersion = new Column();
		
		pkCol.setName("PK_VIEW_VERSION");
		pkCol.setType("INT");
		pkCol.setNullable(false);
		
		tableName.setName("VIEW_NAME");
		tableName.setType("STRING");
		tableName.setNullable(false);
		
		tableSchemaVersion.setName("VIEW_SCHEMA_VERSION");
		tableSchemaVersion.setType("STRING");
		tableSchemaVersion.setNullable(false);
		
		addColumn(pkCol);
		addColumn(tableName);
		addColumn(tableSchemaVersion);
		
		TableKey pk = new TableKey(KeyType.PRIMARY_KEY);
		pk.setName("PRIMARY_KEY_CONSTRAINT");
		pk.addColumn(pkCol);
		
		TableKey uniqueName = new TableKey(KeyType.UNIQUE_KEY);
		uniqueName.setName("VIEW_NAME_CONSTRAINT");
		uniqueName.addColumn(tableName);
		
		addKey(pk);
		addKey(uniqueName);
	}
}
