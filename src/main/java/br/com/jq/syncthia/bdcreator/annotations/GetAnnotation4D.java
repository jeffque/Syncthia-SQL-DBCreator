package br.com.jq.syncthia.bdcreator.annotations;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

public class GetAnnotation4D implements GetAnnotationInterface {
	@Override
	public Table getRelatedTable(Class<? extends TableEntity> entityClass) {
		return null;
	}

	@Override
	public Column[] getColumns(Class<? extends TableEntity> entityClass, Table t) {
		return null;
	}
	@Override
	public TableKey getUniqueKey(Class<? extends TableEntity> entityClass, Table t) {
		// TODO Auto-generated method stub
		return null;
	}
}
