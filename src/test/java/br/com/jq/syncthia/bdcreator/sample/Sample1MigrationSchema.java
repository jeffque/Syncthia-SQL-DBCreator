package br.com.jq.syncthia.bdcreator.sample;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;

public class Sample1MigrationSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new Sample1MigrationTable());
	}

	@Override
	public String getName() {
		return "Sample 1 migration schema";
	}

}
