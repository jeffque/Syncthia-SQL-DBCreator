package br.com.jq.syncthia.bdcreator.schema.basicSchema.table;

import java.sql.SQLException;
import java.sql.Statement;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public class MigratableColumn extends Table {
	public MigratableColumn() {
		setName("MIGRATABLE_COLUMN");
		ColumnAutoIncrement aipk = new ColumnAutoIncrement("PK_MIGRATABLE_COLUMN");
		
		Column tableName = new Column();
		tableName.setName("TABLE_NAME");
		tableName.setNullable(false);
		tableName.setType("STRING");
		
		Column colName = new Column();
		colName.setName("COLUMN_NAME");
		colName.setNullable(false);
		colName.setType("STRING");
		
		Column colType = new Column();
		colType.setName("COLUMN_TYPE");
		colType.setNullable(false);
		colType.setType("STRING");
		
		Column colPrecision1 = new Column();
		colPrecision1.setName("COLUMN_PRECISION1");
		colPrecision1.setNullable(true);
		colPrecision1.setType("INTEGER");
		
		Column colPrecision2 = new Column();
		colPrecision2.setName("COLUMN_PRECISION2");
		colPrecision2.setNullable(true);
		colPrecision2.setType("INTEGER");
		
		Column colNullable = new Column();
		colNullable.setName("COLUMN_NULLABLE");
		colNullable.setNullable(true);
		colNullable.setType("STRING");
		
		Column colPosition = new Column();
		colPosition.setName("COLUMN_POSITION");
		colPosition.setNullable(true);
		colPosition.setType("INTEGER");
		
		addColumn(aipk);
		addColumn(tableName);
		addColumn(colName);
		addColumn(colType);
		addColumn(colPrecision1);
		addColumn(colPrecision2);
		addColumn(colNullable);
		addColumn(colPosition);
		
		TableKey uniqueTableColumn = new TableKey(KeyType.UNIQUE_KEY);
		uniqueTableColumn.setName("TABLE_COLUMN_NAME_CONSTRAINT");
		uniqueTableColumn.addColumn(tableName);
		uniqueTableColumn.addColumn(colName);
		addKey(uniqueTableColumn);
	}

	@Override
	public boolean getCachePreparedStmt() {
		return true;
	}
}
