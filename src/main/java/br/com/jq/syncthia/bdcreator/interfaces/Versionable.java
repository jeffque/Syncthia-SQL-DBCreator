package br.com.jq.syncthia.bdcreator.interfaces;

public interface Versionable {
	public String getDesiredVersion();
	public void setDesiredVersion(String desiredVersion);
	
	public String getRegisteredVersion();
	public void setRegisteredVersion(String registeredVersion);
}
