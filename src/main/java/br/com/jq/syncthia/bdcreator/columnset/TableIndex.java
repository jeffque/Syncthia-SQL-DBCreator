package br.com.jq.syncthia.bdcreator.columnset;

import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.table.Table;

public class TableIndex implements ColumnSetInterface {
	protected List<Column> columnList;
	protected Table table;
	protected String name;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	@Override
	public void addColumn(Column col) {
		columnList.add(col);
	}
	
	@Override
	public List<Column> getColumns() {
		return columnList;
	}
	
	@Override
	public Column getColumn(String colName) {
		for (Column col: columnList) {
			if (colName.equals(col.getName())) {
				return col;
			}
		}
		
		return null;
	}

	public TableIndex() {
		columnList = new ArrayList<Column>();
	}
}
