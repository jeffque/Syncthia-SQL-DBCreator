package br.com.jq.syncthia.sample;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationSqlCommandStrategy;

public class Sample1MigrationTable extends Table {
	public Sample1MigrationTable() {
		setName("SAMPLE_TABLE_1_MIGR");
		
		Column nome = new Column();
		nome.setName("NAME");
		nome.setSize(50);
		nome.setType("VARCHAR");
		Column idade = new Column();
		idade.setName("AGE");
		idade.setType("INT");
		
		addColumn(nome);
		addColumn(idade);
		
		setDesiredVersion("v2");
		setRegisteredVersion("v1");
		
		MigrationSqlCommandStrategy migration1 = new MigrationSqlCommandStrategy();
		migration1.setNewVersion("v2");
		migration1.setOldVersion("v1");
		migration1.setSqlMigration("ALTER TABLE SAMPLE_TABLE_1_MIGR ADD COLUMN NAME VARCHAR(50)");
		
		addMigrationStrategy(migration1);
	}
}
