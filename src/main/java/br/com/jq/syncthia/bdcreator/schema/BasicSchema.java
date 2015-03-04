package br.com.jq.syncthia.bdcreator.schema;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.MigratableVersion;

public class BasicSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new MigratableVersion());
	}

	@Override
	public String getSchemaName() {
		return "Basic Structural Scheme";
	}

}
