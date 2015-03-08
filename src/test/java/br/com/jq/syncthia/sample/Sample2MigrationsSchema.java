package br.com.jq.syncthia.sample;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;

public class Sample2MigrationsSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new Sample2MigrationsTable());
	}

	@Override
	public String getName() {
		return "Sample 2 migrations schema";
	}

}
