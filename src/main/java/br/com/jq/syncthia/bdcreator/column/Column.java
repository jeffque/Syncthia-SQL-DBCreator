package br.com.jq.syncthia.bdcreator.column;

import br.com.jq.syncthia.bdcreator.table.Selectable;

public class Column {
	private Selectable origin;
	private String name, type;
	private Integer precision1, precision2;
	private Boolean nullable;
	private int position;
	private Collation collation;

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
		// Let us considerate that column position starts with 0
		setPosition(origin.getColumns().size());
		this.origin = origin;
	}

	public String colDescription() {
		return name
				+ " "
				+ type
				+ (precision1 != null ? "(" + precision1
						+ (precision2 != null ? "," + precision2 : "") + ")"
						: "")
				+ (collation != null? " COLLATE " + collation: "")
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Collation getCollation() {
		return collation;
	}

	public void setCollation(Collation collation) {
		this.collation = collation;
	}
}
