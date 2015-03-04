package br.com.jq.syncthia.bdcreator.schema;

import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.View;

public abstract class SchemaCreator {
	public abstract String getSchemaName();
	protected abstract void schemaDefinition();
	
	protected List<Table> schemaTables = new ArrayList<Table>();
	protected List<View> schemaViews = new ArrayList<View>();
	
	protected void addTable(Table t) {
		schemaTables.add(t);
	}
	
	protected void addView(View v) {
		schemaViews.add(v);
	}
	
	protected void createSchema() {
		for (Table t: schemaTables) {
			t.createUnit();
		}
		for (View v: schemaViews) {
			v.createUnit();
		}
	}
	
	protected void migrateSchema() {
		for (Table t: schemaTables) {
			t.doMigrations();
		}
		for (View v: schemaViews) {
			v.doMigrations();
		}
	}
	
	@Override
	public String toString() {
		return "SchemaCreator [schemaName()=" + getSchemaName() + "]";
	}
	
	public List<Table> getTables() {
		return schemaTables;
	}
	
	public List<View> getViews() {
		return schemaViews;
	}
	
	public Table getTable(String tableName) {
		for (Table t: schemaTables) {
			if (tableName.equals(t.getName())) {
				return t;
			}
		}
		
		return null;
	}
	
	public View getView(String viewName) {
		for (View v: schemaViews) {
			if (viewName.equals(v.getName())) {
				return v;
			}
		}
		
		return null;
	}
	
	public void saveSchema() {
		for (Table t: schemaTables) {
			t.saveMigratable();
		}
		
		for (View v: schemaViews) {
			v.saveMigratable();
		}
	}
	
}
