package br.com.jq.syncthia.bdcreator.schema.basicSchema.entity;

import br.com.jq.syncthia.bdcreator.annotations.ColumnMapper;
import br.com.jq.syncthia.bdcreator.annotations.TableMapper;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.MigratableVersion;
import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

@TableMapper(table = MigratableVersion.class, uniqueKeyUsed = "MIGRATABLE_NAME_CONSTRAINT")
@ColumnMapper(column = "MIGRATABLE_NAME")
@ColumnMapper(column = "MIGRATABLE_SCHEMA_NAME")
@ColumnMapper(column = "MIGRATABLE_SCHEMA_VERSION")
@ColumnMapper(column = "MIGRATABLE_TYPE")
public class MigratableVersionEntity extends TableEntity {
	
	private String migratableName;
	private String migratableSchemaName;
	private String migratableSchemaVersion;
	private String migratableType;

	public MigratableVersionEntity() {
		migratableName = "";
		migratableSchemaName = "";
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

	public String getMigratableSchemaName() {
		return migratableSchemaName;
	}

	public void setMigratableSchemaName(String migratableSchema) {
		this.migratableSchemaName = migratableSchema;
	}

	public String getMigratableType() {
		return migratableType;
	}

	public void setMigratableType(String migratableType) {
		this.migratableType = migratableType;
	}

	public static MigratableVersionEntity getEntity(MigratableSelectable t) {
		MigratableVersionEntity entity = new MigratableVersionEntity();
		
		entity.setMigratableName(t.getName());
		entity.setMigratableSchemaName(t.getSchema() != null? t.getSchema().getName(): "");
		entity.setMigratableSchemaVersion(t.getDesiredVersion());
		entity.setMigratableType(t.getMigratableType());
		
		return entity;
	}
}
