package br.com.jq.syncthia.bdcreator.table;

import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.exception.DuplicatedPrimaryKeyException;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public class Table {
	protected List<MigrationStrategy> migrations;
	protected List<Column> columnsList;
	protected List<TableKey> keys;
	
	public Table() {
		migrations = new ArrayList<MigrationStrategy>();
		columnsList = new ArrayList<Column>();
		keys = new ArrayList<TableKey>();
	}
	
	public void addKey(TableKey key) throws DuplicatedPrimaryKeyException {
		if (key.getKeyType() == KeyType.PRIMARY_KEY) {
			for (TableKey k: keys) {
				if (k.getKeyType() == KeyType.PRIMARY_KEY) {
					throw new DuplicatedPrimaryKeyException();
				}
			}
		}
		
		keys.add(key);
		key.setTable(this);
	}
	
	public void addColumn(Column col) {
		col.setTable(this);
		columnsList.add(col);
	}
	
	protected String desiredVersion, schemaVersion;
	protected String tableName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDesiredVersion() {
		return desiredVersion;
	}

	public void setDesiredVersion(String desiredVersion) {
		this.desiredVersion = desiredVersion;
	}

	public String getSchemaVersion() {
		return schemaVersion;
	}

	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}
	
	public void addMigrationStrategy(MigrationStrategy migration) {
		migrations.add(migration);
	}
	
	public void doMigrations() {
		doMigrations(desiredVersion, schemaVersion);
	}

	private void doMigrations(String leafVersion, String rootVersion) {
		for (MigrationStrategy migration: migrations) {
			if (leafVersion.equals(migration.getNewVersion())) {
				if (!leafVersion.equals(rootVersion)) {
					doMigrations(migration.getOldVersion(), rootVersion);
					migration.migrateTable();
				}
			}
		}
	}

	public void dropTable() {
		System.out.println("DROP TABLE " + tableName);
	}

	public void createUnit() {
		StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (\n");
		listColumnsSql(sql);
		listKeyMetadata(sql);
		sql.append(")");
		
		System.out.println(sql.toString());
	}

	protected StringBuilder listKeyMetadata(StringBuilder sql) {
		// TODO Auto-generated method stub
		return sql;
	}

	protected StringBuilder listColumnsSql(StringBuilder sql) {
		boolean firstTime = true;
		for (Column col: columnsList) {
			if (!firstTime) {
				sql.append(",\n");
			} else {
				firstTime = false;
			}
			sql.append("\t" + col.colDescription());
		}
		
		return sql.append("\n");
	}

	@Override
	public String toString() {
		return "Table [tableName=" + tableName + "]";
	}
	
	
}
