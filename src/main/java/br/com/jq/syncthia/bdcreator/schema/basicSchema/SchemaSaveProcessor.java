package br.com.jq.syncthia.bdcreator.schema.basicSchema;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.schema.SchemaPostProcessor;

public class SchemaSaveProcessor extends SchemaPostProcessor {

	@Override
	public void processSchemaCreator(SchemaCreator schema) {
		schema.saveSchema();
	}

}
