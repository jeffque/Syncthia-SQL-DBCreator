package br.com.jq.syncthia.bdcreator.schema;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.entity.RegisteredSchemaEntity;
import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public abstract class SchemaCreator extends SchemaDefinitor {
	protected abstract void schemaDefinition();
	
	protected void createSchema() {
		for (MigratableSelectable m: getMigratables()) {
			m.createUnit();
		}
	}
	
	protected void migrateSchema() {
		for (MigratableSelectable m: getMigratables()) {
			m.doMigrations();
		}
	}
	
	@Override
	public String toString() {
		return "SchemaCreator [schemaName()=" + getSchemaName() + "]";
	}
	
	public boolean saveSchema() {
		boolean persisted = true;
		if (getConnection() != null) {
			RegisteredSchemaEntity entity = new RegisteredSchemaEntity();
			
			entity.setSchemaName(getSchemaName());
			entity.setSchemaVersion(getDesiredVersion());
			
			persisted = persisted && entity.persistEntity(getConnection());
		}
		
		
		for (MigratableSelectable m: getMigratables()) {
			persisted = persisted && m.saveMigratable();
		}
		
		return persisted;
	}
	
	
}
