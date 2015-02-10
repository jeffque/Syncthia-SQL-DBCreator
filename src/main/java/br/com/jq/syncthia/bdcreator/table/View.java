package br.com.jq.syncthia.bdcreator.table;

import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;
import br.com.jq.syncthia.bdcreator.table.migration.ViewMigrationBasicStrategy;

public class View extends Table {
	private MigrationStrategy viewMigration;
	
	public View() {
		super();
		
		viewMigration = new ViewMigrationBasicStrategy(this);
	}

	@Override
	public void doMigrations() {
		if (migrations.size() == 0) {
			viewMigration.migrateTable();
		} else {
			super.doMigrations();
		}
	}

	@Override
	public void dropTable() {
		System.out.println("DROP VIEW " + tableName);
	}

	@Override
	public void createUnit() {
		StringBuilder sql = new StringBuilder("CREATE VIEW " + tableName + " (\n");
		listColumnsSql(sql);
		sql.append(")");
		System.out.println(sql.toString());
	}
	
	

}
