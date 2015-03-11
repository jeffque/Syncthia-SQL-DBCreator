package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.reflect.Method;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollectionInternal;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

interface GetAnnotationInterface {
	public Table getRelatedTable(Class<? extends TableEntity> entityClass, SchemaCollectionInternal schemaCollection);
	public List<Column> getColumns(Class<? extends TableEntity> entityClass, Table t);
	public TableKey getUniqueKey(Class<? extends TableEntity> entityClass, Table t);
	public Method getGetterFromColumn(Class<? extends TableEntity> entityClass, String colName) throws NoSuchMethodException, SecurityException;
}
