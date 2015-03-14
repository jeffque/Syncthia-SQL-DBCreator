package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

public class GetAnnotation implements GetAnnotationInterface {
	@Override
	public Table getRelatedTable(Class<? extends TableEntity> entityClass, SchemaCollection schemaCollection) {
		TableMapper tableMapper = entityClass.getAnnotation(TableMapper.class);
		if (tableMapper != null) {
			return schemaCollection.getTable(tableMapper.table());
		}
		
		return null;
	}

	@Override
	public List<Column> getColumns(Class<? extends TableEntity> entityClass, Table t) {
		List<Column> l = new ArrayList<Column>();
		
		for (ColumnMapper columnMapper: entityClass.getAnnotationsByType(ColumnMapper.class)) {
			Column c = t.getColumn(columnMapper.column());
			l.add(c);
		}
		
		return l;
	}

	@Override
	public TableKey getUniqueKey(Class<? extends TableEntity> entityClass, Table t) {
		TableMapper tableMapper = entityClass.getAnnotation(TableMapper.class);
		if (tableMapper != null) {
			if (AnnotationsUtils.invalidString.equals(tableMapper.uniqueKeyUsed())) {
				return t.getPrimaryKey();
			} else {
				return t.getKey(tableMapper.uniqueKeyUsed());
			}
		}
		
		return null;
	}

	@Override
	public Method getGetterFromColumn(Class<? extends TableEntity> entityClass, String colName) throws NoSuchMethodException, SecurityException {
		for (ColumnMapper columnMapper: entityClass.getAnnotationsByType(ColumnMapper.class)) {
			if (colName.equals(columnMapper.column())) {
				String getterName = AnnotationsUtils.generateGetterName(columnMapper.columnEntityGetter(), columnMapper.column());
				
				return entityClass.getMethod(getterName);
			}
		}
		
		return null;
	}
}
