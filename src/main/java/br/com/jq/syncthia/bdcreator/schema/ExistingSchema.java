package br.com.jq.syncthia.bdcreator.schema;

import java.sql.SQLException;

class ExistingSchema extends SchemaDefinitor {
	protected String schemaName = "";

	@Override
	public void setName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	@Override
	public String getName() {
		return schemaName;
	}

	@Override
	public void createUnit() throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void dropUnit() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void migrateUnit() throws SQLException {
		throw new UnsupportedOperationException();
	}

}
