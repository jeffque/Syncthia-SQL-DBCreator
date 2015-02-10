package br.com.jq.syncthia.bdcreator.column;

public enum ColumnType { //XXX trabalhar mais nisso depois
	INT, CHAR, VARCHAR, DECIMAL, BLOB, DATE, DATETIME;
	
	public int getDefaultSize() {
		int size = 1;
		switch (this) {
		case BLOB:
			break;
		case CHAR:
			break;
		case DECIMAL:
			break;
		case INT:
			size = 4;
			break;
		case VARCHAR:
			break;
		default:
			break;
		
		}
		
		return size;
	}
}
