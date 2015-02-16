package br.com.jq.syncthia.bdcreator.table.migration;

import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public abstract class MigrationStrategy {
	private String oldVersion, newVersion;
	protected MigratableSelectable migratable;

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

	public MigratableSelectable getMigratable() {
		return migratable;
	}

	public void setMigratable(MigratableSelectable migratable) {
		this.migratable = migratable;
	}
	
	public abstract void migrateUnit();
}
