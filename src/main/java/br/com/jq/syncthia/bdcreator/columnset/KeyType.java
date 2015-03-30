package br.com.jq.syncthia.bdcreator.columnset;

public enum KeyType {
	PRIMARY_KEY, UNIQUE_KEY; //FOREIGN_KEY is treated elsewhere

	public String keySql() {
		switch (this) {
		case PRIMARY_KEY:
			return "PRIMARY KEY";
		case UNIQUE_KEY:
			return "UNIQUE";
		default:
			return "";
		}
	}
}
