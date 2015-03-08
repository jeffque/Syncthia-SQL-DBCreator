package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

public class GetAnnotation implements GetAnnotationInterface {
	public Table getRelatedTable(Class<? extends TableEntity> entityClass) {
		TableMapper tableMapper = entityClass.getAnnotation(TableMapper.class);
		if (tableMapper != null) {
			try {
				return tableMapper.table().newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			return t.getKey(tableMapper.uniqueKeyUsed());
		}
		
		return null;
	}

	@Override
	public Method getGetterFromColumn(Class<? extends TableEntity> entityClass, String colName) throws NoSuchMethodException, SecurityException {
		for (ColumnMapper columnMapper: entityClass.getAnnotationsByType(ColumnMapper.class)) {
			if (colName.equals(columnMapper.column())) {
				String getterName = columnMapper.columnEntityGetter();
				if (getterName.equals(ColumnMapper.invalidString)) {
					StringBuilder getterBuilder = new StringBuilder("get");
					for (String part: columnMapper.column().split("_")) {
						getterBuilder.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
					}
					
					getterName = getterBuilder.toString();
				}
				
				return entityClass.getMethod(getterName);
			}
		}
		
		return null;
	}
}
