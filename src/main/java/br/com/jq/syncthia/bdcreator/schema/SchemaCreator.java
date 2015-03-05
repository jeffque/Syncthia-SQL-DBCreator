package br.com.jq.syncthia.bdcreator.schema;

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
	
	public void saveSchema() {
		for (MigratableSelectable m: getMigratables()) {
			m.saveMigratable();
		}
	}
	
	
}
