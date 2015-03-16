package br.com.jq.syncthia.bdcreator.columnset;

import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.interfaces.Nameable;

public interface ColumnSetInterface extends Nameable {
	public void addColumn(Column col);
	
	public List<Column> getColumns();
	
	public Column getColumn(String colName);
}
