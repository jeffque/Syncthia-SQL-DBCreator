package br.com.jq.syncthia.bdcreator.schema.basicSchema.entity;

import br.com.jq.syncthia.bdcreator.annotations.AIPKColMapper;
import br.com.jq.syncthia.bdcreator.annotations.ColumnMapper;
import br.com.jq.syncthia.bdcreator.annotations.TableMapper;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.table.RegisteredSchema;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

@TableMapper(table = RegisteredSchema.class, uniqueKeyUsed = "SCHEMA_NAME_CONSTRAINT")
@ColumnMapper(column = "SCHEMA_NAME")
@ColumnMapper(column = "SCHEMA_VERSION")
@AIPKColMapper(column = "PK_REGISTERED_SCHEMA")
public class RegisteredSchemaEntity extends TableEntity {	
	private String schemaName;
	private String schemaVersion;
	private Integer pkRegisteredSchema;
	
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

	public Integer getPkRegisteredSchema() {
		return pkRegisteredSchema;
	}

	public void setPkRegisteredSchema(Integer pkRegisteredSchema) {
		this.pkRegisteredSchema = pkRegisteredSchema;
	}
	
	
}
