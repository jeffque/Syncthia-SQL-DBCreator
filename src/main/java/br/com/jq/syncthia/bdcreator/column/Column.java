package br.com.jq.syncthia.bdcreator.column;

import br.com.jq.syncthia.bdcreator.table.Selectable;

public class Column {
	private Selectable origin;
	private String name, type;
	private Integer precision1, precision2;
	private Boolean nullable;

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

	public Selectable getOrigin() {
		return origin;
	}

	public void setOrigin(Selectable origin) {
		this.origin = origin;
	}

	public String colDescription() {
		return name
				+ " "
				+ type
				+ (precision1 != null ? "(" + precision1
						+ (precision2 != null ? "," + precision2 : "") + ")"
						: "")
				+ (nullable != null ? (nullable ? " NULL" : " NOT NULL") : "");
	}

	public void setSize(Integer size) {
		precision1 = size;
		precision2 = null;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public Boolean getNullable() {
		return nullable;
	}
}
