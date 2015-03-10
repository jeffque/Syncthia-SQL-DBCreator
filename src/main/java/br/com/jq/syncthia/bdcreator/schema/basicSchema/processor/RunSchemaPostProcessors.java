package br.com.jq.syncthia.bdcreator.schema.basicSchema.processor;

import java.sql.SQLException;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.schema.SchemaPostProcessor;

public class RunSchemaPostProcessors extends SchemaPostProcessor {

	@Override
	public void processSchemaCreator(SchemaCreator schema) throws SQLException {
		schema.postProcessIteration();
	}

}
