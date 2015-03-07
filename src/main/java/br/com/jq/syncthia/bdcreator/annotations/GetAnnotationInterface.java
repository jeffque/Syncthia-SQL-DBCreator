package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.reflect.Field;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

interface GetAnnotationInterface {
	public Table getRelatedTable(Class<? extends TableEntity> entityClass);
	public List<Column> getColumns(Class<? extends TableEntity> entityClass, Table t);
	public TableKey getUniqueKey(Class<? extends TableEntity> entityClass, Table t);
	public Field getFieldFromColumn(Class<? extends TableEntity> entityClass, String colName);
}
