package br.com.jq.syncthia.bdcreator.table;

import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.exception.DuplicatedPrimaryKeyException;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public class Table extends MigratableSelectable {
	protected List<MigrationStrategy> migrations;
	protected List<TableKey> keys;
	
	public Table() {
		migrations = new ArrayList<MigrationStrategy>();
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
	
	public void addMigrationStrategy(MigrationStrategy migration) {
		migrations.add(migration);
	}
	
	@Override
	public void doMigrations() {
		for (MigrationStrategy migration: getDesiredMigrations()) {
			migration.migrateUnit();
		}
	}

	@Override
	public List<MigrationStrategy> getDesiredMigrations() {
		return getDesiredMigrations(new ArrayList<MigrationStrategy>(), desiredVersion, schemaVersion);
	}
	
	private List<MigrationStrategy> getDesiredMigrations(List<MigrationStrategy> list, String leafVersion, String rootVersion) {
		for (MigrationStrategy migration: migrations) {
			if (leafVersion.equals(migration.getNewVersion())) {
				if (!leafVersion.equals(rootVersion)) {
					getDesiredMigrations(list, migration.getOldVersion(), rootVersion);
					list.add(migration);
				}
			}
		}
		return list;
	}

	@Override
	public void dropUnit() {
		System.out.println("DROP TABLE " + name);
	}

	@Override
	public void createUnit() {
		StringBuilder sql = new StringBuilder("CREATE TABLE " + name + " (\n");
		listColumnsSql(sql);
		listKeyMetadata(sql);
		sql.append(")");
		
		System.out.println(sql.toString());
	}

	protected StringBuilder listKeyMetadata(StringBuilder sql) {
		// TODO Auto-generated method stub
		return sql;
	}

	@Override
	public String toString() {
		return "Table [tableName=" + name + "]";
	}
	
	
}
