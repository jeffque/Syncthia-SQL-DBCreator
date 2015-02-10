package br.com.jq.syncthia.bdcreator.table.migration;

public class MigrationSqlCommandStrategy extends MigrationStrategy {
	private String sqlMigration;
	
	@Override
	public void migrateTable() {
		System.out.println(sqlMigration);
	}

	public String getSqlMigration() {
		return sqlMigration;
	}

	public void setSqlMigration(String sqlMigration) {
		this.sqlMigration = sqlMigration;
	}
}
