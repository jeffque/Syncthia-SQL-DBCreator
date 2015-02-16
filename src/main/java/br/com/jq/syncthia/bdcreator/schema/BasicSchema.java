package br.com.jq.syncthia.bdcreator.schema;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.TableVersion;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.ViewVersion;

public class BasicSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new TableVersion());
		addTable(new ViewVersion());
	}

	@Override
	public String getSchemaName() {
		return "Basic Structural Scheme";
	}

}
