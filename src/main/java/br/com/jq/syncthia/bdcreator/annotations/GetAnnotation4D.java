package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.reflect.Method;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

public class GetAnnotation4D implements GetAnnotationInterface {
	@Override
	public Table getRelatedTable(Class<? extends TableEntity> entityClass, SchemaCollection schemaCollection) {
		return null;
	}

	@Override
	public List<Column> getColumns(Class<? extends TableEntity> entityClass, Table t) {
		return null;
	}
	
	@Override
	public TableKey getUniqueKey(Class<? extends TableEntity> entityClass, Table t) {
		return null;
	}
	
	@Override
	public Method getGetterFromColumn(Class<? extends TableEntity> entityClass, String colName) {
		return null;
	}
	
	@Override
	public Column getAIPKCol(Class<? extends TableEntity> entityClass, Table t) {
		return null;
	}
	
	@Override
	public Method getAIPKSetter(Class<? extends TableEntity> entityClass, Table t) throws NoSuchMethodException, SecurityException {
		return null;
	}
}
