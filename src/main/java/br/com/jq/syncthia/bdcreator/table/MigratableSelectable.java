package br.com.jq.syncthia.bdcreator.table;

import java.util.List;

import br.com.jq.syncthia.bdcreator.interfaces.Versionable;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public abstract class MigratableSelectable extends Selectable implements Versionable {
	public abstract void dropUnit();
	public abstract void createUnit();
	public abstract void doMigrations();
	public abstract List<MigrationStrategy> getDesiredMigrations();
	public abstract String getMigratableType();
	
	protected String desiredVersion, registeredVersion;
	protected String name;

	public void saveMigratable() {
		// TODO Auto-generated method stub
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDesiredVersion() {
		return desiredVersion;
	}

	@Override
	public void setDesiredVersion(String desiredVersion) {
		this.desiredVersion = desiredVersion;
	}

	@Override
	public String getRegisteredVersion() {
		return registeredVersion;
	}

	@Override
	public void setRegisteredVersion(String registeredVersion) {
		this.registeredVersion = registeredVersion;
	}
}
