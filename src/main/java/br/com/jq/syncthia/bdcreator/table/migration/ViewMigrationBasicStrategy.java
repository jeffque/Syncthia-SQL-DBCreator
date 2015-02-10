package br.com.jq.syncthia.bdcreator.table.migration;

import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.View;

public class ViewMigrationBasicStrategy extends MigrationStrategy {
	private Table view;

	public ViewMigrationBasicStrategy(View view) {
		this.view = view;
	}

	@Override
	public void migrateTable() {
		view.dropTable();
		view.createUnit();
	}

	
}
