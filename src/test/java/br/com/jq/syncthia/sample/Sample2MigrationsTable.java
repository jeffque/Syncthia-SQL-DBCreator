package br.com.jq.syncthia.sample;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationSqlCommandStrategy;

public class Sample2MigrationsTable extends Table {
	public Sample2MigrationsTable() {
		setName("SAMPLE_TABLE_2_MIGR");
		
		Column nome = new Column();
		nome.setName("NAME");
		nome.setSize(50);
		nome.setType("VARCHAR");
		Column idade = new Column();
		idade.setName("AGE");
		idade.setType("INT");
		Column address = new Column();
		address.setName("ADDRESS");
		address.setType("VARCHAR");
		address.setSize(100);
		
		addColumn(nome);
		addColumn(idade);
		addColumn(address);
		
		setDesiredVersion("v3");
		setRegisteredVersion("v1");
		
		MigrationSqlCommandStrategy migration1 = new MigrationSqlCommandStrategy();
		migration1.setNewVersion("v2");
		migration1.setOldVersion("v1");
		migration1.setSqlMigration("ALTER TABLE SAMPLE_TABLE_2_MIGR ADD COLUMN NAME VARCHAR(50)");
		
		MigrationSqlCommandStrategy migration2 = new MigrationSqlCommandStrategy();
		migration2.setNewVersion("v3");
		migration2.setOldVersion("v2");
		migration2.setSqlMigration("ALTER TABLE SAMPLE_TABLE_2_MIGR ADD COLUMN ADDRESS VARCHAR(100)");
		
		addMigrationStrategy(migration2);
		addMigrationStrategy(migration1);
	}
}
