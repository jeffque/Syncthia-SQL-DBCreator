package br.com.jq.syncthia.bdcreator.annotations;

import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

interface GetAnnotationInterface {
	public Table getRelatedTable(Class<? extends TableEntity> entityClass);
}
