package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.View;

class ExistingSchemaCollection extends SchemaCollectionInternal {
	protected List<ExistingSchema> schemasFromDB;
	
	public ExistingSchemaCollection(Connection sqlConnection) {
		setConnection(sqlConnection);
		schemasFromDB = new ArrayList<ExistingSchema>();
		
		searchMetadata();
	}

	private void searchMetadata() {
		searchSchemas();
		searchTables();
	}

	private void searchSchemas() {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM REGISTERED_SCHEMAS");
			
			
			while(rs.next()) {
				ExistingSchema schema = new ExistingSchema();
				String schemaName = rs.getString("SCHEMA_NAME");
				String schemaVersion = rs.getString("SCHEMA_VERSION");
				
				schema.setSchemaName(schemaName);
				schema.setRegisteredVersion(schemaVersion);
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void searchTables() {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM MIGRATABLE_VERSION");
			
			
			while (rs.next()) {
				String schemaName = rs.getString("MIGRATABLE_SCHEMA_NAME");
				String schemaVersion = rs.getString("MIGRATABLE_SCHEMA_VERSION");
				
				ExistingSchema schema = getExistingSchema(schemaName);
				
				String migratableName = rs.getString("MIGRATABLE_NAME");
				String migratableType = rs.getString("MIGRATABLE_TYPE");
				
				MigratableSelectable m;
				
				switch (migratableType) {
				case "T":
					m = new Table();
					break;
				case "V":
					m = new View();
					break;
				default:
					m = null;
				}
				
				if (m != null) {
					m.setName(migratableName);
					m.setRegisteredVersion(schemaVersion);
					
					switch (migratableType) {
					case "T":
						schema.addTable((Table) m);
						break;
					case "V":
						schema.addView((View) m);
					}
				}
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ExistingSchema getExistingSchema(String schemaName) {
		for (ExistingSchema schema: schemasFromDB) {
			if (schemaName.equals(schema.getSchemaName())) {
				return schema;
			}
		}
		return null;
	}

	public void registerExistingSchema(ExistingSchema schema) {
		registerDefinitor(schema);
		schemasFromDB.add(schema);
	}

	public List<ExistingSchema> getExistingSchemas() {
		return schemasFromDB;
	}

}
