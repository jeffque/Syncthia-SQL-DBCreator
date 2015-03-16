package br.com.jq.syncthia.bdcreator.table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.ColumnSetInterface;
import br.com.jq.syncthia.bdcreator.interfaces.Connectable;

public class Selectable implements Connectable, ColumnSetInterface {
	private String name;
	protected List<Column> columnList;
	private Connection sqlConnection;
	
	public Selectable() {
		columnList = new ArrayList<Column>();
	}
	
	@Override
	public void addColumn(Column col) {
		col.setOrigin(this);
		columnList.add(col);
	}
	
	@Override
	public Column getColumn(String columnName) {
		for (Column col: columnList) {
			if (columnName.equals(col.getName())) {
				return col;
			}
		}
		return null;
	}
	
	@Override
	public List<Column> getColumns() {
		return columnList;
	}
	
	public StringBuilder listColumnsSql(StringBuilder sql) {
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

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
