package br.com.jq.syncthia.bdcreator.schema.basicSchema;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;

public class RegisteredSchemas extends Table {
	public RegisteredSchemas() {
		setName("REGISTERED_SCHEMAS");
		
		Column pkCol = new Column();
		Column schemaName = new Column();
		Column schemaVersion = new Column();
		
		pkCol.setName("PK_MIGRATABLE_VERSION");
		pkCol.setType("INT");
		pkCol.setNullable(false);
		
		schemaName.setName("SCHEMA_NAME");
		schemaName.setType("STRING");
		schemaName.setNullable(false);
		
		schemaVersion.setName("SCHEMA_VERSION");
		schemaVersion.setType("STRING");
		schemaVersion.setNullable(false);
		
		addColumn(pkCol);
		addColumn(schemaName);
		addColumn(schemaVersion);
		
		TableKey pk = new TableKey(KeyType.PRIMARY_KEY);
		pk.setName("PRIMARY_KEY_CONSTRAINT");
		pk.addColumn(pkCol);
		
		TableKey uniqueName = new TableKey(KeyType.UNIQUE_KEY);
		uniqueName.setName("SCHEMA_NAME_CONSTRAINT");
		uniqueName.addColumn(schemaName);
		
		addKey(pk);
		addKey(uniqueName);
	}
}
