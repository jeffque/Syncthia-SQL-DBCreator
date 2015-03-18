package br.com.jq.syncthia.bdcreator.schema.basicSchema.table;

import br.com.jq.syncthia.bdcreator.column.Collation;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;

public class MigratableVersion extends Table {
	public MigratableVersion() {
		setName("MIGRATABLE_VERSION");
		
		ColumnAutoIncrement pkCol = new ColumnAutoIncrement("PK_MIGRATABLE_VERSION");
		Column migratableName = new Column();
		Column migratableSchemaName = new Column();
		Column migratableSchemaVersion = new Column();
		Column migratableType = new Column();
		
		migratableName.setName("MIGRATABLE_NAME");
		migratableName.setType("STRING");
		migratableName.setCollation(Collation.NOCASE);
		migratableName.setNullable(false);
		
		migratableSchemaName.setName("MIGRATABLE_SCHEMA_NAME");
		migratableSchemaName.setType("STRING");
		migratableSchemaName.setCollation(Collation.NOCASE);
		migratableSchemaName.setNullable(false);
		
		migratableSchemaVersion.setName("MIGRATABLE_SCHEMA_VERSION");
		migratableSchemaVersion.setType("STRING");
		migratableSchemaVersion.setCollation(Collation.NOCASE);
		migratableSchemaVersion.setNullable(false);
		
		migratableType.setName("MIGRATABLE_TYPE"); // May be T for table or V for View
		migratableType.setType("STRING");
		migratableType.setCollation(Collation.NOCASE);
		migratableType.setNullable(false);
		
		addColumn(pkCol);
		addColumn(migratableName);
		addColumn(migratableSchemaName);
		addColumn(migratableSchemaVersion);
		addColumn(migratableType);
		
		TableKey uniqueName = new TableKey(KeyType.UNIQUE_KEY);
		uniqueName.setName("MIGRATABLE_NAME_CONSTRAINT");
		uniqueName.addColumn(migratableName);
		
		addKey(uniqueName);
	}
	
	@Override
	public boolean getCachePreparedStmt() {
		return true;
	}
}
