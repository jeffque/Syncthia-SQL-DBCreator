package br.com.jq.syncthia.bdcreator.columnset;

import br.com.jq.syncthia.bdcreator.column.Column;

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

	public String keyDescription() {
		StringBuilder builder = new StringBuilder(" CONSTRAINT ");
		
		builder.append(getName()).append(" ");
		builder.append(keyType.keySql()).append(" (");
		
		boolean firstTime = true;
		for (Column col: columnList) {
			if (!firstTime) {
				builder.append(", ");
			} else {
				firstTime = false;
			}
			builder.append(col.getName());
		}
		builder.append(")");
		
		return builder.toString();
	}
}
