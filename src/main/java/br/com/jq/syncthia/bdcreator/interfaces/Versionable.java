package br.com.jq.syncthia.bdcreator.interfaces;

import br.com.jq.syncthia.bdcreator.schema.SchemaDefinitor;

public interface Versionable {
	public String getDesiredVersion();
	public void setDesiredVersion(String desiredVersion);
	
	public String getRegisteredVersion();
	public void setRegisteredVersion(String registeredVersion);
	
	public SchemaDefinitor getSchema();
}
