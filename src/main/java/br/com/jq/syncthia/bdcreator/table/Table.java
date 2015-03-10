package br.com.jq.syncthia.bdcreator.table;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.columnset.KeyType;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.exceptions.DuplicatedPrimaryKeyException;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationStrategy;

public class Table extends MigratableSelectable {
	protected List<MigrationStrategy> migrations;
	protected List<TableKey> keys;
	protected List<Column> ordinaryColumns;
	private ColumnAutoIncrement aipkCol;
	
	public Table() {
		migrations = new ArrayList<MigrationStrategy>();
		keys = new ArrayList<TableKey>();
		ordinaryColumns = new ArrayList<Column>();
	}
	
	public void addColumn(ColumnAutoIncrement aipkCol) {
		super.addColumn(aipkCol);
		this.aipkCol = aipkCol;
		
		TableKey pkKey = new TableKey(KeyType.PRIMARY_KEY);
		pkKey.setName("PRIMARY_KEY_CONSTRAINT");
		pkKey.addColumn(aipkCol);
		addKey(pkKey);
	}
	
	@Override
	public void addColumn(Column col) {
		super.addColumn(col);
		ordinaryColumns.add(col);
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
	public void doMigrations() throws SQLException {
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
	public boolean dropUnit() throws SQLException {
		boolean okDrop = true;
		String sql = "DROP TABLE " + getName();
		System.out.println(sql);
		
		if (getConnection() != null) {
			Statement stmt = getConnection().createStatement();
			stmt.execute(sql);
			stmt.close();
		} else {
			okDrop = false;
		}
		return okDrop;
	}

	@Override
	public void createUnit() throws SQLException {
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + getName() + " (\n");
		listColumnsSql(sql);
		listKeyMetadata(sql);
		sql.append(")");
		
		System.out.println(sql.toString());
		if (getConnection() != null) {
			Statement stmt = getConnection().createStatement();
			stmt.execute(sql.toString());
			stmt.close();
		}
	}

	public StringBuilder listKeyMetadata(StringBuilder sql) {
		// If there is just one key and is the aipkCol, there is no need to do this
		if (keys.size() == 1 && aipkCol != null) {
			return sql;
		}
		if (keys.size() > 0) {
			sql.append("\t,\n");
		}
		
		boolean firstTime = true;
		for (TableKey k: keys) {
			if (k.getKeyType() == KeyType.PRIMARY_KEY && aipkCol != null) {
				continue;
			}
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
		return "Table [tableName=" + getName() + "]";
	}

	@Override
	public String getMigratableType() {
		return "T";
	}

	public TableKey getKey(String keyName) {
		for (TableKey k: keys) {
			if (keyName.equals(k.getName())) {
				return k;
			}
		}
		
		return null;
	}
	
	public PreparedStatement prepareDeleteStatement() throws SQLException {
		return prepareDeleteStatement(getPrimaryKey());
	}

	public PreparedStatement prepareDeleteStatement(TableKey uniqueKey) throws SQLException {
		if (getConnection() == null) {
			return null;
		}
		
		boolean firstCol;
		StringBuilder deleteSql = new StringBuilder("DELETE FROM ").append(getName()).append(" WHERE ");
		
		firstCol = true;
		for (Column col: uniqueKey.getColumns()) {
			if (!firstCol) {
				deleteSql.append(" AND ");
			} else {
				firstCol = false;
			}
			deleteSql.append(col.getName()).append(" = ?");
		}
		
		System.out.println("delete stmt (" + getName() + "):\n\t" + deleteSql);
		return getConnection().prepareStatement(deleteSql.toString());
	}
	
	public PreparedStatement prepareUpdateStatement() throws SQLException {
		return prepareUpdateStatement(getPrimaryKey(), getColumnList());
	}

	public PreparedStatement prepareUpdateStatement(TableKey uniqueKey,
			List<Column> columnListSignificant) throws SQLException {
		if (getConnection() == null) {
			return null;
		}
		
		boolean firstCol;
		StringBuilder updateSql = new StringBuilder("UPDATE ").append(getName()).append(" SET ");
		
		firstCol = true;
		for (Column col: columnListSignificant) {
			if (!firstCol) {
				updateSql.append(", ");
			} else {
				firstCol = false;
			}
			updateSql.append(col.getName()).append(" = ?");
		}
		
		updateSql.append(" WHERE ");
		
		firstCol = true;
		for (Column col: uniqueKey.getColumns()) {
			if (!firstCol) {
				updateSql.append(" AND ");
			} else {
				firstCol = false;
			}
			updateSql.append(col.getName()).append(" = ?");
		}
		
		System.out.println("update stmt (" + getName() + "):\n\t" + updateSql);
		return getConnection().prepareStatement(updateSql.toString());
	}

	public PreparedStatement prepareInsertStatement() throws SQLException {
		return prepareInsertStatement(getColumnList());
	}
	
	public PreparedStatement prepareInsertStatement(List<Column> columnListSignificant) throws SQLException {
		if (getConnection() == null) {
			return null;
		}
		
		StringBuilder sql = new StringBuilder("INSERT INTO ").append(getName()).append(" (");
		boolean firstCol;
		
		firstCol = true;
		for (Column c: columnListSignificant) {
			if (firstCol) {
				firstCol = false;
			} else {
				sql.append(",");
			}
			sql.append(c.getName());
		}
		sql.append(") VALUES (");
		firstCol = true;
		for (int i = columnListSignificant.size() - 1; i >= 0; i--) {
			if (firstCol) {
				firstCol = false;
			} else {
				sql.append(", ");
			}
			sql.append("?");
		}
		sql.append(")");
		
		System.out.println("insert stmt (" + getName() + "):\n\t" + sql);
		return getConnection().prepareStatement(sql.toString());
	}
	
	
}
