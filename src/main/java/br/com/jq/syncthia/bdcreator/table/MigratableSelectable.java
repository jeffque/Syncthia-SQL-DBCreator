package br.com.jq.syncthia.bdcreator.table;

import java.util.List;

import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public abstract class MigratableSelectable extends Selectable {
	public abstract void dropUnit();
	public abstract void createUnit();
	public abstract void doMigrations();
	public abstract List<MigrationStrategy> getDesiredMigrations();
	
	protected String desiredVersion, schemaVersion;
	protected String name;


	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesiredVersion() {
		return desiredVersion;
	}

	public void setDesiredVersion(String desiredVersion) {
		this.desiredVersion = desiredVersion;
	}

	public String getSchemaVersion() {
		return schemaVersion;
	}

	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}
}
