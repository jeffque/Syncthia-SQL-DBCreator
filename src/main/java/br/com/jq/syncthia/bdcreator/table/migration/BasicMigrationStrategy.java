package br.com.jq.syncthia.bdcreator.table.migration;

import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public class BasicMigrationStrategy extends MigrationStrategy {
	private MigratableSelectable migratable;

	public BasicMigrationStrategy(MigratableSelectable view) {
		this.migratable = view;
	}

	@Override
	public void migrateTable() {
		migratable.dropUnit();
		migratable.createUnit();
	}

	
}
