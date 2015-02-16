package br.com.jq.syncthia.bdcreator.columnset;

public class TableKey extends ColumnSet {
	private KeyType keyType;
	
	public TableKey() {
		this(KeyType.PRIMARY_KEY);
	}
	
	public TableKey(KeyType type) {
		setKeyType(type);
	}

	public KeyType getKeyType() {
		return keyType;
	}

	public void setKeyType(KeyType keyType) {
		this.keyType = keyType;
	}
}
