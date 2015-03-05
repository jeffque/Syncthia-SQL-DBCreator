package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public class SchemaCollection extends SchemaCollectionInternal {
	private List<SchemaCreator> registeredSchemas;
	
	public SchemaCollection() {
		registeredSchemas = new ArrayList<SchemaCreator>();
		registerSchema(new BasicSchema());
	}
	
	@Override
	public void setConnection(Connection sqlConnection) {
		super.setConnection(sqlConnection);
		
		for (SchemaCreator schema: registeredSchemas) {
			schema.setConnection(sqlConnection);
		}
	}
	
	public void registerSchema(SchemaCreator schema) {
		super.registerDefinitor(schema);
		
		registeredSchemas.add(schema);
		schema.schemaDefinition();
		schema.setConnection(getConnection());
	}
	
	private void schemaMetaDataFromExisting() {
		ExistingSchemaCollection existingCollection = new ExistingSchemaCollection(getConnection());
		
		for (ExistingSchema existingSchema: existingCollection.getExistingSchemas()) {
			SchemaCreator counterPartSchema = getSchema(existingSchema.getSchemaName());
			
			for (MigratableSelectable existingMigratable: existingSchema.getMigratables()) {
				MigratableSelectable counterPartMigratable = counterPartSchema.getMigratable(existingMigratable.getName());
				
				counterPartMigratable.setRegisteredVersion(existingMigratable.getRegisteredVersion());
			}
		}
	}
	
	public final void createOrMigrateSchema() {
		schemaMetaDataFromExisting();
		
		// Create all schemas, if not created
		for (SchemaCreator schema: registeredSchemas) {
			schema.createSchema();
		}
		
		// Migrate all schemas, if needed
		for (SchemaCreator schema: registeredSchemas) {
			schema.migrateSchema();
		}
		
		// Save all schemas
		for (SchemaCreator schema: registeredSchemas) {
			schema.saveSchema();
		}
	}
	
	public List<SchemaCreator> getRegisteredSchemas() {
		return registeredSchemas;
	}
	
	public SchemaCreator getSchema(String schemaName) {
		for (SchemaCreator s: registeredSchemas) {
			if (schemaName.equals(s.getSchemaName())) {
				return s;
			}
		}
		
		return null;
	}
	
}
