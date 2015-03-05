package br.com.jq.syncthia.bdcreator.annotations;

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
}
