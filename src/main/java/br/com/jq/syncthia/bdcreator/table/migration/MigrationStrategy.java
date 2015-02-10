package br.com.jq.syncthia.bdcreator.table.migration;

import br.com.jq.syncthia.bdcreator.table.Table;

public abstract class MigrationStrategy {
	private String oldVersion, newVersion;
	private Table table;

	public String getOldVersion() {
		return oldVersion;
	}

	public void setOldVersion(String oldVersion) {
		this.oldVersion = oldVersion;
	}

	public String getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	public abstract void migrateTable();
}
