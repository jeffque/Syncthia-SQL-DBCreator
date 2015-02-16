package br.com.jq.syncthia.bdcreator.schema;

import java.util.ArrayList;
import java.util.List;

public class SchemaCollection {
	private List<SchemaCreator> registeredSchemas;
	
	public SchemaCollection() {
		registeredSchemas = new ArrayList<SchemaCreator>();
		registerSchema(new BasicSchema());
	}
	
	public void registerSchema(SchemaCreator schema) {
		registeredSchemas.add(schema);
		schema.schemaDefinition();
	}
	
	public final void createOrMigrateSchema() {
		for (SchemaCreator schema: registeredSchemas) {
			schema.createSchema();
		}
		
		for (SchemaCreator schema: registeredSchemas) {
			schema.migrateSchema();
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
