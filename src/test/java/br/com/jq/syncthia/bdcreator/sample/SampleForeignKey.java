package br.com.jq.syncthia.bdcreator.sample;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.columnset.ForeignKey;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.table.Table;

public class SampleForeignKey extends SchemaCreator {
	
	class TableParent extends Table {
		TableParent() {
			setName("TABLE_PARENT");
			addColumn(new ColumnAutoIncrement("pk"));
			
			Column name = new Column();
			name.setName("NAME");
			addColumn(name);
		}
	}
	
	class TableChild extends Table {
		TableChild() {
			setName("TABLE_CHILD");
			
			addColumn(new ColumnAutoIncrement("CHILD_PK"));
			
			Column fkCol = new Column();
			fkCol.setName("FK_COL");
			fkCol.setType("INTEGER");
			
			addColumn(fkCol);
			
			ForeignKey fk = new ForeignKey();
			fk.setTableReferedName("TABLE_PARENT");
			fk.addColumn(fkCol);
			
			addForeignKey(fk);
		}
	}

	@Override
	public String getName() {
		return "Sample foreign key test";
	}

	@Override
	protected void schemaDefinition() {
		addTable(new TableParent());
		addTable(new TableChild());
	}

}
