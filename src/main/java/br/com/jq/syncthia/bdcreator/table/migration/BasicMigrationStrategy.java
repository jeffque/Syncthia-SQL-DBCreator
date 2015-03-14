package br.com.jq.syncthia.bdcreator.table.migration;

import java.sql.SQLException;

import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public class BasicMigrationStrategy extends MigrationStrategy {

	public BasicMigrationStrategy(MigratableSelectable migratable) {
		setVersionable(migratable);
	}

	@Override
	public void migrateUnit() throws SQLException {
		getVersionable().dropUnit();
		getVersionable().createUnit();
	}

	
}
