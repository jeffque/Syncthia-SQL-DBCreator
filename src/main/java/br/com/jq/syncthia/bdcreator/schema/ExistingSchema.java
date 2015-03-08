package br.com.jq.syncthia.bdcreator.schema;

class ExistingSchema extends SchemaDefinitor {
	protected String schemaName = "";

	@Override
	public void setName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	@Override
	public String getName() {
		return schemaName;
	}

}
