package br.com.jq.syncthia.bdcreator.schema.basicSchema.entity;

import br.com.jq.syncthia.bdcreator.annotations.ColumnMapper;
import br.com.jq.syncthia.bdcreator.annotations.TableMapper;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.table.MigratableColumn;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

@TableMapper(table = MigratableColumn.class, uniqueKeyUsed = "TABLE_COLUMN_NAME_CONSTRAINT")
@ColumnMapper(column = "TABLE_NAME")
@ColumnMapper(column = "COLUMN_NAME")
@ColumnMapper(column = "COLUMN_TYPE")
@ColumnMapper(column = "COLUMN_PRECISION1")
@ColumnMapper(column = "COLUMN_PRECISION2")
@ColumnMapper(column = "COLUMN_NULLABLE")
@ColumnMapper(column = "COLUMN_POSITION")
public class MigratableColumnEntity extends TableEntity {
	private String tableName;
	private String columnName;
	private String columnType;
	private Integer columnPrecision1;
	private Integer columnPrecision2;
	private Boolean columnNullable;
	private Integer columnPosition;
	
	public static MigratableColumnEntity getInstance(Column col) {
		MigratableColumnEntity entity = new MigratableColumnEntity();
		
		entity.setTableName(col.getOrigin().getName());
		entity.setColumnName(col.getName());
		entity.setColumnType(col.getType());
		entity.setColumnPrecision1(col.getPrecision1());
		entity.setColumnPrecision2(col.getPrecision2());
		entity.setColumnNullable(col.getNullable());
		entity.setColumnPosition(col.getPosition());
		
		return entity;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnType() {
		return columnType;
	}
	
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public Integer getColumnPrecision1() {
		return columnPrecision1;
	}
	
	public void setColumnPrecision1(Integer columnPrecision1) {
		this.columnPrecision1 = columnPrecision1;
	}
	
	public Integer getColumnPrecision2() {
		return columnPrecision2;
	}
	
	public void setColumnPrecision2(Integer columnPrecision2) {
		this.columnPrecision2 = columnPrecision2;
	}
	
	public Boolean getColumnNullable() {
		return columnNullable;
	}
	
	public void setColumnNullable(Boolean columnNullable) {
		this.columnNullable = columnNullable;
	}

	public Integer getColumnPosition() {
		return columnPosition;
	}

	public void setColumnPosition(Integer columnPosition) {
		this.columnPosition = columnPosition;
	}

}
