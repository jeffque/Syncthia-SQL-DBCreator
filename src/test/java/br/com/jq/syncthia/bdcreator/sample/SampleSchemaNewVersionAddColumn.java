package br.com.jq.syncthia.bdcreator.sample;

import br.com.jq.syncthia.bdcreator.annotations.AIPKColMapper;
import br.com.jq.syncthia.bdcreator.annotations.ColumnMapper;
import br.com.jq.syncthia.bdcreator.annotations.TableMapper;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.column.ColumnAutoIncrement;
import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

public class SampleSchemaNewVersionAddColumn extends SchemaCreator {
	
	@TableMapper(table = SampleTableX.class)
	@ColumnMapper(column = "A")
	@ColumnMapper(column = "B")
	@AIPKColMapper(column = "PK")
	public static class SampleTableXEntity extends TableEntity {
		private String a;
		private String b;
		private Integer pk;
		
		public String getA() {
			return a;
		}
		
		public void setA(String a) {
			this.a = a;
		}
		
		public String getB() {
			return b;
		}
		
		public void setB(String b) {
			this.b = b;
		}

		public Integer getPk() {
			return pk;
		}

		public void setPk(Integer pk) {
			this.pk = pk;
		}
	}
	
	public static class SampleTableX extends Table {
		public SampleTableX() {
			setName("SAMPLE_TABLE_X");
			addColumn(new ColumnAutoIncrement("PK"));
			
			Column a = new Column();
			a.setName("A");
			a.setType("STRING");
			addColumn(a);
			
			Column b = new Column();
			b.setName("B");
			b.setType("STRING");
			addColumn(b);
		}
	}

	@Override
	public String getName() {
		return "Schema test migration";
	}

	@Override
	protected void schemaDefinition() {
		setDesiredVersion("v1");
		
		addTable(new SampleTableX());
	}

}
