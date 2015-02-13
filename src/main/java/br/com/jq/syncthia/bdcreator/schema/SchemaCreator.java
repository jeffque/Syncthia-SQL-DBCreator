package br.com.jq.syncthia.bdcreator.schema;

import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.View;

public abstract class SchemaCreator {
	protected abstract void schemaDefinition();
	
	protected List<Table> schemaTables = new ArrayList<Table>();
	protected List<View> schemaViewes = new ArrayList<View>();
	
	private static List<SchemaCreator> registeredSchemas;
	
	static {
		registeredSchemas = new ArrayList<SchemaCreator>();
		registerSchema(new BasicSchema());
	}
	
	protected void addTable(Table t) {
		schemaTables.add(t);
	}
	
	protected void addView(View t) {
		schemaViewes.add(t);
	}
	
	protected void createSchema() {
		for (Table t: schemaTables) {
			t.createUnit();
		}
		for (View v: schemaViewes) {
			v.createUnit();
		}
	}
	
	protected void migrateSchema() {
		for (Table t: schemaTables) {
			t.doMigrations();
		}
		for (View v: schemaViewes) {
			v.doMigrations();
		}
	}
	
	public static void registerSchema(SchemaCreator schema) {
		registeredSchemas.add(schema);
		schema.schemaDefinition();
	}
	
	public static final void createOrMigrateSchema() {
		for (SchemaCreator schema: registeredSchemas) {
			schema.createSchema();
		}
		
		for (SchemaCreator schema: registeredSchemas) {
			schema.migrateSchema();
		}
	}
}
