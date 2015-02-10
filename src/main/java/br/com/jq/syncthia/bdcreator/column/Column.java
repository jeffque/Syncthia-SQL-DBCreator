package br.com.jq.syncthia.bdcreator.column;

import br.com.jq.syncthia.bdcreator.table.Table;

public class Column {
	private Table table;
	private String name, type;
	private Integer precision1, precision2;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPrecision1() {
		return precision1;
	}
	public void setPrecision1(Integer precision1) {
		this.precision1 = precision1;
	}
	public Integer getPrecision2() {
		return precision2;
	}
	public void setPrecision2(Integer precision2) {
		this.precision2 = precision2;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	
	public String colDescription() {
		return name + " " + type + (precision1 != null? "(" + precision1 + (precision2 != null? "," + precision2: "") + ")": "");
	}
	public void setSize(Integer size) {
		precision1 = size;
		precision2 = null;
	}
}
