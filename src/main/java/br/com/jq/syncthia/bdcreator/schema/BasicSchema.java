package br.com.jq.syncthia.bdcreator.schema;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.TableVersion;

public class BasicSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new TableVersion());
	}

	@Override
	public String getSchemaName() {
		return "Basic Structural Scheme";
	}

}
