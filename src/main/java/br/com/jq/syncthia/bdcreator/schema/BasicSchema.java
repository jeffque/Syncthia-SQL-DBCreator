package br.com.jq.syncthia.bdcreator.schema;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.MigratableVersion;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.RegisteredSchemas;

public class BasicSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new MigratableVersion());
		addTable(new RegisteredSchemas());
	}

	@Override
	public String getSchemaName() {
		return "Basic Structural Scheme";
	}

}
