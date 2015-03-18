package br.com.jq.syncthia.bdcreator.schema.basicSchema.table;

import br.com.jq.syncthia.bdcreator.column.Collation;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;

public class MigratableColumn extends Table {
	public MigratableColumn() {
		setName("MIGRATABLE_COLUMN");
		ColumnAutoIncrement aipk = new ColumnAutoIncrement("PK_MIGRATABLE_COLUMN");
		
		Column tableName = new Column();
		tableName.setName("TABLE_NAME");
		tableName.setType("STRING");
		tableName.setCollation(Collation.NOCASE);
		tableName.setNullable(false);
		
		Column colName = new Column();
		colName.setName("COLUMN_NAME");
		colName.setType("STRING");
		colName.setCollation(Collation.NOCASE);
		colName.setNullable(false);
		
		Column colType = new Column();
		colType.setName("COLUMN_TYPE");
		colType.setType("STRING");
		colType.setCollation(Collation.NOCASE);
		colType.setNullable(false);
		
		Column colPrecision1 = new Column();
		colPrecision1.setName("COLUMN_PRECISION1");
		colPrecision1.setType("INTEGER");
		colPrecision1.setNullable(true);
		
		Column colPrecision2 = new Column();
		colPrecision2.setName("COLUMN_PRECISION2");
		colPrecision2.setType("INTEGER");
		colPrecision2.setNullable(true);
		
		Column colNullable = new Column();
		colNullable.setName("COLUMN_NULLABLE");
		colNullable.setType("STRING");
		colNullable.setCollation(Collation.NOCASE);
		colNullable.setNullable(true);
		
		Column colPosition = new Column();
		colPosition.setName("COLUMN_POSITION");
		colPosition.setType("INTEGER");
		colPosition.setNullable(true);
		
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
