package br.com.jq.syncthia.bdcreator.schema.basicSchema;

import java.sql.SQLException;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.schema.SchemaProcessor;

public class SchemaCreatorProcessor extends SchemaProcessor {

	@Override
	public void processSchemaCreator(SchemaCreator schema) throws SQLException {
		schema.createSchema();
	}

}
