package br.com.jq.syncthia.bdcreator.table.migration;

import java.sql.SQLException;

public class MigrationSqlCommandStrategy extends MigrationStrategy {
	private String sqlMigration;
	
	@Override
	public void migrateUnit() throws SQLException {
		if (getVersionable().getSchema().getConnection() != null) {
			getVersionable().getSchema().getConnection().createStatement().execute(getSqlMigration());
		}
	}

	public String getSqlMigration() {
		return sqlMigration;
	}

	public void setSqlMigration(String sqlMigration) {
		this.sqlMigration = sqlMigration;
	}
}
