package br.com.jq.syncthia.bdcreator.schema.basicSchema.processor;

import java.sql.SQLException;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.schema.SchemaPostProcessor;
import br.com.jq.syncthia.bdcreator.table.Table;

public class CloseSchemaMetadaProcessor extends SchemaPostProcessor {

	@Override
	public void processSchemaCreator(SchemaCreator schema) throws SQLException {
		for (Table t: schema.getTables()) {
			if (t.getCachePreparedStmt()) {
				t.dropAllCachePreparedStmt();
			}
		}

	}

}
