package br.com.jq.syncthia.bdcreator.column;


public class ColumnAutoIncrement extends Column {
	public ColumnAutoIncrement(String name) {
		super.setName(name);
	}
	
	public void setName(String name) {
	}

	public String getType() {
		return "INTEGER PRIMARY KEY";
	}

	public String colDescription() {
		return getName()
				+ " "
				+ getType();
	}

	public void setSize(Integer size) {
	}

	public void setNullable(Boolean nullable) {
	}

}
