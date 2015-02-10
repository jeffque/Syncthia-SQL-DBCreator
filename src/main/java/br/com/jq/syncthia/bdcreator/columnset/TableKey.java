package br.com.jq.syncthia.bdcreator.columnset;

public class TableKey extends ColumnSet {
	private KeyType keyType;
	
	public TableKey() {
		super();
		setKeyType(KeyType.NOT_KEY);
	}

	public KeyType getKeyType() {
		return keyType;
	}

	public void setKeyType(KeyType keyType) {
		this.keyType = keyType;
	}
}
