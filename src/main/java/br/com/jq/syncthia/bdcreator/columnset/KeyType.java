package br.com.jq.syncthia.bdcreator.columnset;

public enum KeyType {
	PRIMARY_KEY, UNIQUE_KEY; //TODO FOREING_KEY

	public String keySql() {
		switch (this) {
		case PRIMARY_KEY:
			return "PRIMARY KEY";
		case UNIQUE_KEY:
			return "UNIQUE KEY";
		default:
			return "";
		}
	}
}
