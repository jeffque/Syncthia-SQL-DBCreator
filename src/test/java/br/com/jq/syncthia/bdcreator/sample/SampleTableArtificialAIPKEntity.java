package br.com.jq.syncthia.bdcreator.sample;

import br.com.jq.syncthia.bdcreator.annotations.AIPKColMapper;
import br.com.jq.syncthia.bdcreator.annotations.ColumnMapper;
import br.com.jq.syncthia.bdcreator.annotations.TableMapper;
import br.com.jq.syncthia.bdcreator.table.TableEntity;

@TableMapper(table=SampleTableArtificialAIPK.class, uniqueKeyUsed="UNIQUE_NAME")
@ColumnMapper(column="NAME")
@AIPKColMapper(column="PK")
public class SampleTableArtificialAIPKEntity extends TableEntity {
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
