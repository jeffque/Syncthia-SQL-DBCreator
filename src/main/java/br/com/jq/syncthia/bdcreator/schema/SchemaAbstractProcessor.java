package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;

import br.com.jq.syncthia.bdcreator.interfaces.Connectable;

public abstract class SchemaAbstractProcessor implements Connectable {
	private Connection sqlConnection;

	@Override
	public void setConnection(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}

	@Override
	public Connection getConnection() {
		return sqlConnection;
	}
	
	public abstract void processSchemaCreator(SchemaCreator schema);

}
