package br.com.jq.syncthia.bdcreator.schema;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.MigratableVersion;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.RegisteredSchema;

public class BasicSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new MigratableVersion());
		addTable(new RegisteredSchema());
		setDesiredVersion("v1");
	}

	@Override
	public String getSchemaName() {
		return "Basic Structural Scheme";
	}

}
