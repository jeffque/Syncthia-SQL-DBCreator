package br.com.jq.syncthia.bdcreator.table;

import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;

public class Selectable {
	protected List<Column> columnsList;
	
	public Selectable() {
		columnsList = new ArrayList<Column>();
	}
	
	public void addColumn(Column col) {
		col.setOrigin(this);
		columnsList.add(col);
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

}
