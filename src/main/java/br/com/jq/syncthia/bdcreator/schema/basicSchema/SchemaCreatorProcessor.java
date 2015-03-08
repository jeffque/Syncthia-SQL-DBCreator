package br.com.jq.syncthia.bdcreator.schema.basicSchema;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.schema.SchemaProcessor;

public class SchemaCreatorProcessor extends SchemaProcessor {

	@Override
	public void processSchemaCreator(SchemaCreator schema) {
		schema.createSchema();
	}

}
