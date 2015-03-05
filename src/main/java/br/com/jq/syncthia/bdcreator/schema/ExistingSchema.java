package br.com.jq.syncthia.bdcreator.schema;

class ExistingSchema extends SchemaDefinitor {
	protected String schemaName = "";

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	@Override
	public String getSchemaName() {
		return schemaName;
	}

}
