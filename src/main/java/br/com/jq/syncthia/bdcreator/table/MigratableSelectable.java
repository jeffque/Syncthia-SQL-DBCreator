package br.com.jq.syncthia.bdcreator.table;

public abstract class MigratableSelectable extends Selectable {
	public abstract void dropUnit();
	public abstract void createUnit();
	public abstract void doMigrations();
	
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
