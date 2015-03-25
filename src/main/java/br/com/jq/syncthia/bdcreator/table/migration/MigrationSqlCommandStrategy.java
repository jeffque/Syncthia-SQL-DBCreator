package br.com.jq.syncthia.bdcreator.table.migration;

import java.sql.SQLException;

public class MigrationSqlCommandStrategy extends MigrationStrategy {
	private String sqlMigration;
	
	@Override
	public void migrateUnit() throws SQLException {
		getVersionable().getSchema().getConnection().createStatement().execute(sqlMigration);
	}

	public String getSqlMigration() {
		return sqlMigration;
	}

	public void setSqlMigration(String sqlMigration) {
		this.sqlMigration = sqlMigration;
	}
}
