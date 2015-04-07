package br.com.jq.syncthia.bdcreator.columnset;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.table.Table;

// For now, it only supports referring to the primary key of another table; maybe in the future it may refer to a specific column set
//TODO on delete/on update actions
//TODO deferred key support
public class ForeignKey extends TableColumnSet {
	private Table tableRefered;
	private String tableReferedName;

	public Table getTableRefered() {
		if (tableRefered == null && tableReferedName != null) {
			// A refered table may be in another schema within the same scehma collection
			tableRefered = getTable().getSchema().getSchemaCollection().getTable(tableReferedName);
		}
		return tableRefered;
	}

	public void setTableRefered(Table tableRefered) {
		this.tableRefered = tableRefered;
	}

	public String getTableReferedName() {
		if (tableReferedName == null && tableRefered != null) {
			tableReferedName = tableRefered.getName();
		}
		return tableReferedName;
	}

	public void setTableReferedName(String tableReferedName) {
		this.tableReferedName = tableReferedName;
	}

	public String keyDescription() {
		StringBuilder builder = new StringBuilder();
		
		if (getName() != null) {
			builder.append(" CONSTRAINT ");
			builder.append(getName()).append(" ");
		}
		
		builder.append(" FOREIGN KEY (");
		
		boolean firstTime = true;
		for (Column col: columnList) {
			if (!firstTime) {
				builder.append(", ");
			} else {
				firstTime = false;
			}
			builder.append(col.getName());
		}
		builder.append(") REFERENCES ").append(getTableReferedName());
		
		return builder.toString();
	}
}
