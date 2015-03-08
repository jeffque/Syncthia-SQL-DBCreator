package br.com.jq.syncthia.bdcreator.schema.basicSchema;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;

public class BasicSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new MigratableVersion());
		addTable(new RegisteredSchema());
		setDesiredVersion("v1");
	}

	@Override
	public String getName() {
		return "Basic Structural Scheme";
	}

}
