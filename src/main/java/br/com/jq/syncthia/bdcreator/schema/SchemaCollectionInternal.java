package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.interfaces.Connectable;

class SchemaCollectionInternal implements Connectable {
	private Connection sqlConnection;
	
	private List<SchemaDefinitor> registeredDefinitors;
	
	public SchemaCollectionInternal() {
		registeredDefinitors = new ArrayList<SchemaDefinitor>();
	}
	
	@Override
	public Connection getConnection() {
		return sqlConnection;
	}

	@Override
	public void setConnection(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	public void registerDefinitor(SchemaDefinitor definitor) {
		registeredDefinitors.add(definitor);
	}
	
	public SchemaDefinitor getDefinitor(String definitorName) {
		for (SchemaDefinitor definitor: registeredDefinitors) {
			if (definitorName.equals(definitor.getSchemaName())) {
				return definitor;
			}
		}
		
		return null;
	}

}
