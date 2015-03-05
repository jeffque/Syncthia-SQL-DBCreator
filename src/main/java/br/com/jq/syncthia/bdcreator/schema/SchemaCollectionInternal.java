package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;

import br.com.jq.syncthia.bdcreator.interfaces.Connectable;

class SchemaCollectionInternal implements Connectable {
	private Connection sqlConnection;
	
	@Override
	public Connection getConnection() {
		return sqlConnection;
	}

	@Override
	public void setConnection(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}

}
