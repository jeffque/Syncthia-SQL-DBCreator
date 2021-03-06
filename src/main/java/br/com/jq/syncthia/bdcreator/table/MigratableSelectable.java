package br.com.jq.syncthia.bdcreator.table;

import br.com.jq.syncthia.bdcreator.interfaces.Versionable;
import br.com.jq.syncthia.bdcreator.schema.SchemaDefinitor;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.entity.MigratableVersionEntity;

public abstract class MigratableSelectable extends Selectable implements Versionable {
	public abstract String getMigratableType();
	
	protected String desiredVersion, registeredVersion;
	
	private SchemaDefinitor schema;
	
	public MigratableSelectable() {
		desiredVersion = "";
		registeredVersion = "";
	}

	public boolean saveMigratable() {
		if (getConnection() != null) {
			return MigratableVersionEntity.getEntity(this).persistEntity(getSchema().getSchemaCollection());
		}
		
		return false;
	}

	@Override
	public String getDesiredVersion() {
		return desiredVersion;
	}

	@Override
	public void setDesiredVersion(String desiredVersion) {
		this.desiredVersion = desiredVersion;
	}

	@Override
	public String getRegisteredVersion() {
		return registeredVersion;
	}

	@Override
	public void setRegisteredVersion(String registeredVersion) {
		this.registeredVersion = registeredVersion;
	}
	
	@Override
	public SchemaDefinitor getSchema() {
		return schema;
	}
	
	public void setSchema(SchemaDefinitor schema) {
		this.schema = schema;
	}
}
