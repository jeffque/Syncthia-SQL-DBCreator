package br.com.jq.syncthia.bdcreator.sample;

import br.com.jq.syncthia.bdcreator.annotations.AIPKColMapper;
import br.com.jq.syncthia.bdcreator.annotations.ColumnMapper;
import br.com.jq.syncthia.bdcreator.annotations.TableMapper;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

@TableMapper(table = SampleTableNocase.class, uniqueKeyUsed = "UNIQUE_NAME_CONSTRAINT")
@ColumnMapper(column = "NAME")
@AIPKColMapper(column = "PK")
public class SampleEntityNocase extends TableEntity {
	private String name;
	private Integer pk;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getPk() {
		return pk;
	}
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}

}
