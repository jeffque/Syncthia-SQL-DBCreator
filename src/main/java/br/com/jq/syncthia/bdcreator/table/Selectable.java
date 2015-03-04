package br.com.jq.syncthia.bdcreator.table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.interfaces.Connectable;

public class Selectable implements Connectable {
	protected List<Column> columnList;
	private Connection sqlConnection;
	
	public Selectable() {
		columnList = new ArrayList<Column>();
	}
	
	public void addColumn(Column col) {
		col.setOrigin(this);
		columnList.add(col);
	}
	
	public Column getColumn(String columnName) {
		for (Column col: columnList) {
			if (columnName.equals(col.getName())) {
				return col;
			}
		}
		return null;
	}
	
	public List<Column> getColumnList() {
		return columnList;
	}
	
	protected StringBuilder listColumnsSql(StringBuilder sql) {
		boolean firstTime = true;
		for (Column col: columnList) {
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
	public void setConnection(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	@Override
	public Connection getConnection() {
		return sqlConnection;
	}

}
