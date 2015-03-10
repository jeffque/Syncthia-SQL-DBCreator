package br.com.jq.syncthia.bdcreator.schema.basicSchema.processor;

import java.sql.SQLException;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.schema.SchemaPreProcessor;

public class RunSchemaPreProcessors extends SchemaPreProcessor {

	@Override
	public void processSchemaCreator(SchemaCreator schema) throws SQLException {
		schema.preProcessIteration();
	}

}
