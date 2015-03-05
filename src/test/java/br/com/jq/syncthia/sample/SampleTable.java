package br.com.jq.syncthia.sample;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.table.Table;

public class SampleTable extends Table {
	public SampleTable() {
		setName("SAMPLE_TABLE");
		
		Column idade = new Column();
		idade.setName("AGE");
		idade.setType("INT");
		
		addColumn(idade);
		
		setDesiredVersion("v1");
		setRegisteredVersion("v1");
	}
}
