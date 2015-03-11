package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.interfaces.Connectable;
import br.com.jq.syncthia.bdcreator.table.Table;

public class SchemaCollectionInternal implements Connectable {
	private Connection sqlConnection;
	private SchemaCollectionInternal rootCollection;
	
	private List<SchemaDefinitor> registeredDefinitors;
	
	public SchemaCollectionInternal() {
		registeredDefinitors = new ArrayList<SchemaDefinitor>();
		rootCollection = this;
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
		definitor.setSchemaCollection(rootCollection);
	}
	
	public SchemaDefinitor getDefinitor(String definitorName) {
		for (SchemaDefinitor definitor: registeredDefinitors) {
			if (definitorName.equals(definitor.getName())) {
				return definitor;
			}
		}
		
		return null;
	}

	public Table getTable(Class<? extends Table> tclazz) {
		Table t = null;
		
		for (SchemaDefinitor schema: registeredDefinitors) {
			t = schema.getTable(tclazz);
			if (t != null) {
				return t;
			}
		}
		
		return t;
	}
	
	public Table getTable(String tableName) {
		Table t = null;
		
		for (SchemaDefinitor schema: registeredDefinitors) {
			t = schema.getTable(tableName);
			if (t != null) {
				return t;
			}
		}
		
		return t;
	}

}
