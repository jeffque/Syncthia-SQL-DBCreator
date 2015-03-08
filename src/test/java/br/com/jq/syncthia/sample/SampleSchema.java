package br.com.jq.syncthia.sample;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;

public class SampleSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new SampleTable());
	}

	@Override
	public String getName() {
		return "Sample schema";
	}

}
