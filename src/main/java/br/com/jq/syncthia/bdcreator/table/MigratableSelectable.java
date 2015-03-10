package br.com.jq.syncthia.bdcreator.table;

import java.sql.SQLException;
import java.util.List;

import br.com.jq.syncthia.bdcreator.interfaces.Versionable;
import br.com.jq.syncthia.bdcreator.schema.SchemaDefinitor;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.entity.MigratableVersionEntity;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public abstract class MigratableSelectable extends Selectable implements Versionable {
	public abstract boolean dropUnit() throws SQLException;
	public abstract void createUnit() throws SQLException;
	public abstract void doMigrations() throws SQLException;
	public abstract List<MigrationStrategy> getDesiredMigrations();
	public abstract String getMigratableType();
	
	protected String desiredVersion, registeredVersion;
	
	private SchemaDefinitor schema;
	
	public MigratableSelectable() {
		desiredVersion = "";
		registeredVersion = "";
	}

	public boolean saveMigratable() {
		if (getConnection() != null) {
			return MigratableVersionEntity.getEntity(this).persistEntity(getConnection());
		}
		
		return false;
	}

	@Override
	public String getDesiredVersion() {
		return desiredVersion;
	}

	@Override
	public void setDesiredVersion(String desiredVersion) {
		this.desiredVersion = desiredVersion;
	}

	@Override
	public String getRegisteredVersion() {
		return registeredVersion;
	}

	@Override
	public void setRegisteredVersion(String registeredVersion) {
		this.registeredVersion = registeredVersion;
	}
	
	@Override
	public SchemaDefinitor getSchema() {
		return schema;
	}
	
	public void setSchema(SchemaDefinitor schema) {
		this.schema = schema;
	}
}
