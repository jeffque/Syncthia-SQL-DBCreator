package br.com.jq.syncthia.bdcreator.table;

import java.sql.SQLException;
import java.sql.Statement;
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
	
	public void addKey(TableKey newKey) throws DuplicatedPrimaryKeyException {
		if (newKey.getKeyType() == KeyType.PRIMARY_KEY) {
			for (TableKey k: keys) {
				if (k.getKeyType() == KeyType.PRIMARY_KEY) {
					throw new DuplicatedPrimaryKeyException();
				}
			}
		}
		
		keys.add(newKey);
		newKey.setTable(this);
	}
	
	public List<TableKey> getKeys() {
		return keys;
	}
	
	public TableKey getPrimaryKey() {
		for (TableKey k: keys) {
			if (k.getKeyType() == KeyType.PRIMARY_KEY) {
				return k;
			}
		}
		
		return null;
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
		return getDesiredMigrations(new ArrayList<MigrationStrategy>(), desiredVersion, registeredVersion);
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
		String sql = "DROP TABLE " + name;
		System.out.println(sql);
		
		if (getConnection() != null) {
			try {
				Statement stmt = getConnection().createStatement();
				stmt.execute(sql);
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void createUnit() {
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + name + " (\n");
		listColumnsSql(sql);
		listKeyMetadata(sql);
		sql.append(")");
		
		System.out.println(sql.toString());
		if (getConnection() != null) {
			try {
				Statement stmt = getConnection().createStatement();
				stmt.execute(sql.toString());
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected StringBuilder listKeyMetadata(StringBuilder sql) {
		if (keys.size() > 0) {
			sql.append("\t,\n");
		}
		
		boolean firstTime = true;
		for (TableKey k: keys) {
			if (!firstTime) {
				sql.append(",\n");
			} else {
				firstTime = false;
			}
			sql.append("\t" + k.keyDescription());
		}
		
		return sql.append("\n");
	}

	@Override
	public String toString() {
		return "Table [tableName=" + name + "]";
	}

	@Override
	public void saveMigratable() {
		// TODO Auto-generated method stub
		
	}
	
	
}
