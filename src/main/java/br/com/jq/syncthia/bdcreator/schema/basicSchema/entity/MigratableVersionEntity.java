package br.com.jq.syncthia.bdcreator.schema.basicSchema.entity;

import br.com.jq.syncthia.bdcreator.annotations.ColumnMapper;
import br.com.jq.syncthia.bdcreator.annotations.TableMapper;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.MigratableVersion;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

@TableMapper(table = MigratableVersion.class, uniqueKeyUsed = "MIGRATABLE_NAME_CONSTRAINT")
public class MigratableVersionEntity extends TableEntity {
	@ColumnMapper(column = "MIGRATABLE_NAME")
	public String migratableName;
	@ColumnMapper(column = "MIGRATABLE_SCHEMA_NAME")
	public String migratableSchema;
	@ColumnMapper(column = "MIGRATABLE_SCHEMA_VERSION")
	public String migratableSchemaVersion;
	@ColumnMapper(column = "MIGRATABLE_TYPE")
	public String migratableType;
	
	public MigratableVersionEntity() {
		migratableName = "";
		migratableSchema = "";
		migratableSchemaVersion = "";
		migratableType = "";
	}

	public String getMigratableName() {
		return migratableName;
	}
	
	public void setMigratableName(String migratableName) {
		this.migratableName = migratableName;
	}
	
	public String getMigratableSchemaVersion() {
		return migratableSchemaVersion;
	}
	
	public void setMigratableSchemaVersion(String migratableSchemaVersion) {
		this.migratableSchemaVersion = migratableSchemaVersion;
	}

	public String getMigratableSchema() {
		return migratableSchema;
	}

	public void setMigratableSchema(String migratableSchema) {
		this.migratableSchema = migratableSchema;
	}

	public String getMigratableType() {
		return migratableType;
	}

	public void setMigratableType(String migratableType) {
		this.migratableType = migratableType;
	}
}
