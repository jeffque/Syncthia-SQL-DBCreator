package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.interfaces.Connectable;

public class SchemaCollection implements Connectable {
	private List<SchemaCreator> registeredSchemas;
	
	private Connection sqlConnection;
	
	public SchemaCollection() {
		registeredSchemas = new ArrayList<SchemaCreator>();
		registerSchema(new BasicSchema());
	}
	
	@Override
	public Connection getConnection() {
		return sqlConnection;
	}
	
	@Override
	public void setConnection(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
		
		for (SchemaCreator schema: registeredSchemas) {
			schema.setConnection(sqlConnection);
		}
	}
	
	public void registerSchema(SchemaCreator schema) {
		registeredSchemas.add(schema);
		schema.schemaDefinition();
		schema.setConnection(sqlConnection);
	}
	
	public final void createOrMigrateSchema() {
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
