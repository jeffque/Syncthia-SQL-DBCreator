package br.com.jq.syncthia.bdcreator.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.com.jq.syncthia.bdcreator.schema.SchemaDefinitor;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public interface Versionable {
	public String getDesiredVersion();
	public void setDesiredVersion(String desiredVersion);
	
	public String getRegisteredVersion();
	public void setRegisteredVersion(String registeredVersion);
	
	public SchemaDefinitor getSchema();
	public List<MigrationStrategy> getDesiredMigrations();
	
	public void dropUnit() throws SQLException;
	public void createUnit() throws SQLException;
	public void migrateUnit() throws SQLException;
}
