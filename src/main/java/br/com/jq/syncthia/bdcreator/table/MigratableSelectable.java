package br.com.jq.syncthia.bdcreator.table;

import java.util.List;

import br.com.jq.syncthia.bdcreator.interfaces.Versionable;
import br.com.jq.syncthia.bdcreator.schema.SchemaDefinitor;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.entity.MigratableVersionEntity;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public abstract class MigratableSelectable extends Selectable implements Versionable {
	public abstract boolean dropUnit();
	public abstract void createUnit();
	public abstract void doMigrations();
	public abstract List<MigrationStrategy> getDesiredMigrations();
	public abstract String getMigratableType();
	
	protected String desiredVersion, registeredVersion;
	protected String name;
	
	private SchemaDefinitor schema;
	
	public MigratableSelectable() {
		desiredVersion = "";
		registeredVersion = "";
		name = "";
	}

	public boolean saveMigratable() {
		if (getConnection() != null) {
			MigratableVersionEntity entity = new MigratableVersionEntity();
			
			entity.setMigratableName(getName());
			entity.setMigratableSchema(getSchema() != null? getSchema().getSchemaName(): "");
			entity.setMigratableSchemaVersion(getDesiredVersion());
			entity.setMigratableType(getMigratableType());
			
			return entity.persistEntity(getConnection());
		}
		
		return false;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
