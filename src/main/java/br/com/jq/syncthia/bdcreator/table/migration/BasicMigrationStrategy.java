package br.com.jq.syncthia.bdcreator.table.migration;

import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public class BasicMigrationStrategy extends MigrationStrategy {

	public BasicMigrationStrategy(MigratableSelectable migratable) {
		setMigratable(migratable);
	}

	@Override
	public void migrateUnit() {
		migratable.dropUnit();
		migratable.createUnit();
	}

	
}
