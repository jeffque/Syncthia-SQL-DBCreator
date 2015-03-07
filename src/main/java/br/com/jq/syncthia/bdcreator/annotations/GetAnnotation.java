package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.reflect.Field;
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
		
		for (Field f: entityClass.getFields()) {
			ColumnMapper columnMapper = f.getAnnotation(ColumnMapper.class);
			
			if (columnMapper != null) {
				Column c = t.getColumn(columnMapper.column());
				l.add(c);
			}
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
	public Field getFieldFromColumn(Class<? extends TableEntity> entityClass, String colName) {
		for (Field f: entityClass.getFields()) {
			ColumnMapper columnMapper = f.getAnnotation(ColumnMapper.class);
			
			if (columnMapper != null && colName.equals(columnMapper.column())) {
				return f;
			}
		}
		
		return null;
	}
}
