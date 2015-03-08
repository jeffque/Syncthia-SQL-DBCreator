package br.com.jq.syncthia.bdcreator.schema;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.entity.RegisteredSchemaEntity;
import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public abstract class SchemaCreator extends SchemaDefinitor {
	protected abstract void schemaDefinition();
	
	public void createSchema() {
		for (MigratableSelectable m: getMigratables()) {
			m.createUnit();
		}
	}
	
	public void migrateSchema() {
		for (MigratableSelectable m: getMigratables()) {
			m.doMigrations();
		}
	}
	
	@Override
	public String toString() {
		return "SchemaCreator [schemaName()=" + getName() + "]";
	}
	
	public boolean saveSchema() {
		boolean persisted = true;
		if (getConnection() != null) {
			RegisteredSchemaEntity entity = new RegisteredSchemaEntity();
			
			entity.setSchemaName(getName());
			entity.setSchemaVersion(getDesiredVersion());
			
			persisted = persisted && entity.persistEntity(getConnection());
		}
		
		
		for (MigratableSelectable m: getMigratables()) {
			persisted = persisted && m.saveMigratable();
		}
		
		return persisted;
	}
	
	
}
