package br.com.jq.syncthia.bdcreator.columnset;

import br.com.jq.syncthia.bdcreator.table.Table;

public class ForeignKey extends TableColumnSet {
	private Table tableRefered;
	private String tableReferedName;

	public Table getTableRefered() {
		return tableRefered;
	}

	public void setTableRefered(Table tableRefered) {
		this.tableRefered = tableRefered;
	}

	public String getTableReferedName() {
		return tableReferedName;
	}

	public void setTableReferedName(String tableReferedName) {
		this.tableReferedName = tableReferedName;
	}

}
