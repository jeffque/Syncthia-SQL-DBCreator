package br.com.jq.syncthia.bdcreator.schema.basicSchema.table;

import br.com.jq.syncthia.bdcreator.column.Collation;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;

public class RegisteredSchema extends Table {
	public RegisteredSchema() {
		setName("REGISTERED_SCHEMA");
		
		ColumnAutoIncrement pkCol = new ColumnAutoIncrement("PK_REGISTERED_SCHEMA");
		Column schemaName = new Column();
		Column schemaVersion = new Column();
		
		schemaName.setName("SCHEMA_NAME");
		schemaName.setType("STRING");
		schemaName.setCollation(Collation.NOCASE);
		schemaName.setNullable(false);
		
		schemaVersion.setName("SCHEMA_VERSION");
		schemaVersion.setType("STRING");
		schemaVersion.setCollation(Collation.NOCASE);
		schemaVersion.setNullable(false);
		
		addColumn(pkCol);
		addColumn(schemaName);
		addColumn(schemaVersion);
		
		TableKey uniqueName = new TableKey(KeyType.UNIQUE_KEY);
		uniqueName.setName("SCHEMA_NAME_CONSTRAINT");
		uniqueName.addColumn(schemaName);
		
		addKey(uniqueName);
	}
	
	@Override
	public boolean getCachePreparedStmt() {
		return true;
	}
}
