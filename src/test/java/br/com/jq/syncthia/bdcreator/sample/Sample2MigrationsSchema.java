package br.com.jq.syncthia.bdcreator.sample;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;

public class Sample2MigrationsSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new Sample2MigrationsTable());
		setDesiredVersion("v3");
	}

	@Override
	public String getName() {
		return "Sample 2 migrations schema";
	}

}
