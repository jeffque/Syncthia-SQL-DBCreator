package br.com.jq.syncthia.bdcreator.schema.basicSchema.entity;

import br.com.jq.syncthia.bdcreator.annotations.ColumnMapper;
import br.com.jq.syncthia.bdcreator.annotations.TableMapper;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.RegisteredSchema;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

@TableMapper(table = RegisteredSchema.class, uniqueKeyUsed = "SCHEMA_NAME_CONSTRAINT")
public class RegisteredSchemaEntity extends TableEntity {
	@ColumnMapper(column = "SCHEMA_NAME")
	public String schemaName;
	@ColumnMapper(column = "SCHEMA_VERSION")
	public String schemaVersion;
	
	public String getSchemaName() {
		return schemaName;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public String getSchemaVersion() {
		return schemaVersion;
	}
	
	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}
	
	
}
