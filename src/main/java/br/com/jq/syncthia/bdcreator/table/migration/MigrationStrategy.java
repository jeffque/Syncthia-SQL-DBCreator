package br.com.jq.syncthia.bdcreator.table.migration;

import java.sql.SQLException;

import br.com.jq.syncthia.bdcreator.interfaces.Versionable;

public abstract class MigrationStrategy {
	private String oldVersion, newVersion;
	private Versionable versionable;

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

	public Versionable getVersionable() {
		return versionable;
	}

	public void setVersionable(Versionable versionable) {
		this.versionable = versionable;
	}
	
	public abstract void migrateUnit() throws SQLException;
}
