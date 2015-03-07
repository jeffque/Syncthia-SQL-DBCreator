package br.com.jq.syncthia.sample;

import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.table.Table;

public class SampleTableAIPK extends Table {
	public SampleTableAIPK() {
		addColumn(new ColumnAutoIncrement("PK"));
	}
}
