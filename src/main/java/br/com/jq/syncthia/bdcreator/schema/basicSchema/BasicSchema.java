package br.com.jq.syncthia.bdcreator.schema.basicSchema;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.processor.CloseSchemaMetadaProcessor;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.table.MigratableColumn;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.table.MigratableVersion;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.table.RegisteredSchema;

public class BasicSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		addTable(new MigratableVersion());
		addTable(new RegisteredSchema());
		addTable(new MigratableColumn());
		setDesiredVersion("v1");
		
		addPostProcessor(new CloseSchemaMetadaProcessor());
	}

	@Override
	public String getName() {
		return "Basic Structural Scheme";
	}

}
