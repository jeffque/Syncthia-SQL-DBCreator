package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.interfaces.Connectable;
import br.com.jq.syncthia.bdcreator.interfaces.Nameable;
import br.com.jq.syncthia.bdcreator.interfaces.Versionable;
import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.View;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public abstract class SchemaDefinitor implements Connectable, Versionable, Nameable {
	@Override
	public void setName(String name) {
		// a priori, it shan't be possible
		throw new UnsupportedOperationException();
	}
	
	private SchemaCollection rootCollection;
	private List<Table> schemaTables;
	private List<View> schemaViews;
	private List<MigratableSelectable> schemaMigratables;
	
	private String registeredVersion;
	private String desiredVersion;
	
	public SchemaDefinitor() {
		schemaTables = new ArrayList<Table>();
		schemaViews = new ArrayList<View>();
		schemaMigratables = new ArrayList<MigratableSelectable>();
		
		registeredVersion = "";
		desiredVersion = "";
	}
	
	private Connection sqlConnection;
	
	@Override
	public String getDesiredVersion() {
		return desiredVersion;
	}
	
	@Override
	public void setDesiredVersion(String desiredVersion) {
		this.desiredVersion = desiredVersion;
		
		for (MigratableSelectable m: getMigratables()) {
			m.setDesiredVersion(desiredVersion);
		}
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
	public Connection getConnection() {
		return sqlConnection;
	}
	
	@Override
	public void setConnection(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
		
		for (MigratableSelectable m: getMigratables()) {
			m.setConnection(sqlConnection);
		}
	}
	
	public List<Table> getTables() {
		return schemaTables;
	}
	
	public List<View> getViews() {
		return schemaViews;
	}
	
	public List<MigratableSelectable> getMigratables() {
		return schemaMigratables;
	}
	
	public Table getTable(String tableName) {
		for (Table t: schemaTables) {
			if (tableName.equals(t.getName())) {
				return t;
			}
		}
		
		return null;
	}
	
	public Table getTable(Class<? extends Table> tclazz) {
		for (Table t: schemaTables) {
			if (t.getClass() == tclazz) {
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
	
	public MigratableSelectable getMigratable(String migratableName) {
		for (MigratableSelectable m: schemaMigratables) {
			if (migratableName.equals(m.getName())) {
				return m;
			}
		}
		
		return null;
	}
	
	protected void addTable(Table t) {
		addMigratable(t);
		schemaTables.add(t);
	}

	protected void addView(View v) {
		addMigratable(v);
		schemaViews.add(v);
	}
	
	private void addMigratable(MigratableSelectable m) {
		schemaMigratables.add(m);
		m.setSchema(this);
		m.setDesiredVersion(getDesiredVersion());
		m.setConnection(getConnection());
	}
	
	@Override
	public SchemaDefinitor getSchema() {
		return this;
	}
	
	@Override
	public void dropUnit() throws SQLException {
		for (MigratableSelectable m: getMigratables()) {
			m.dropUnit();
		}
		
		Statement stmt = getConnection().createStatement();
		stmt.executeUpdate("DELETE FROM MIGRATABLE_VERSION WHERE MIGRATABLE_SCHEMA_NAME ='" + getName() + "'");
		stmt.executeUpdate("DELETE FROM REGISTERED_SCHEMA WHERE SCHEMA_NAME ='" + getName() + "'");
	}
	
	@Override
	public List<MigrationStrategy> getDesiredMigrations() {
		List<MigrationStrategy> migrations = new ArrayList<MigrationStrategy>();
		for (MigratableSelectable m: getMigratables()) {
			migrations.addAll(m.getDesiredMigrations());
		}
		
		return migrations;
	}
	
	public void addMigrationStrategyTable(MigrationStrategy migration, String tableName) {
		getTable(tableName).addMigrationStrategy(migration);
	}

	public void setSchemaCollection(SchemaCollection rootCollection) {
		this.rootCollection = rootCollection;
	}

	public SchemaCollection getSchemaCollection() {
		return rootCollection;
	}
}
