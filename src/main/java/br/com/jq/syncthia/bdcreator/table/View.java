package br.com.jq.syncthia.bdcreator.table;

import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;
import br.com.jq.syncthia.bdcreator.table.migration.BasicMigrationStrategy;

public class View extends MigratableSelectable {
	private MigrationStrategy viewMigration;
	
	public View() {
		super();
		
		viewMigration = new BasicMigrationStrategy(this);
	}

	@Override
	public void doMigrations() {
		viewMigration.migrateTable();
	}

	@Override
	public void dropUnit() {
		System.out.println("DROP VIEW " + name);
	}

	@Override
	public void createUnit() {
		StringBuilder sql = new StringBuilder("CREATE VIEW " + name + " (\n");
		listColumnsSql(sql);
		sql.append(")");
		System.out.println(sql.toString());
	}
	
	

}
