package br.com.jq.syncthia.bdcreator.columnset;

import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.table.Table;

public abstract class ColumnSet {
	protected List<Column> columnList;
	protected Table table;
	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public ColumnSet() {
		columnList = new ArrayList<Column>();
	}
}
