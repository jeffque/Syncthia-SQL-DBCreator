package br.com.jq.syncthia.bdcreator.schema.basicSchema.entity;

import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.annotations.AIPKColMapper;
import br.com.jq.syncthia.bdcreator.annotations.ColumnMapper;
import br.com.jq.syncthia.bdcreator.annotations.TableMapper;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.table.MigratableVersion;
import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

@TableMapper(table = MigratableVersion.class, uniqueKeyUsed = "MIGRATABLE_NAME_CONSTRAINT")
@ColumnMapper(column = "MIGRATABLE_NAME")
@ColumnMapper(column = "MIGRATABLE_SCHEMA_NAME")
@ColumnMapper(column = "MIGRATABLE_SCHEMA_VERSION")
@ColumnMapper(column = "MIGRATABLE_TYPE")
@AIPKColMapper(column = "PK_MIGRATABLE_VERSION")
public class MigratableVersionEntity extends TableEntity {
	List<MigratableColumnEntity> columnsEntities;
	private String migratableName;
	private String migratableSchemaName;
	private String migratableSchemaVersion;
	private String migratableType;
	
	private Integer pkMigratableVersion;

	public MigratableVersionEntity() {
		migratableName = "";
		migratableSchemaName = "";
		migratableSchemaVersion = "";
		migratableType = "";
		
		columnsEntities = new ArrayList<MigratableColumnEntity>();
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
		
		entity.setColumnsEntity(t);
		
		return entity;
	}

	private void setColumnsEntity(MigratableSelectable t) {
		for (Column col: t.getColumnList()) {
			columnsEntities.add(MigratableColumnEntity.getInstance(col));
		}
		
	}

	@Override
	public boolean persistEntity(SchemaCollection schemaCollection) {
		boolean ret = super.persistEntity(schemaCollection);
		
		for (MigratableColumnEntity colEntity: columnsEntities) {
			ret = ret && colEntity.persistEntity(schemaCollection);
		}
		return ret;
	}

	public Integer getPkMigratableVersion() {
		return pkMigratableVersion;
	}

	public void setPkMigratableVersion(Integer pkMigratableVersion) {
		this.pkMigratableVersion = pkMigratableVersion;
	}
	
}
