package br.com.jq.syncthia.bdcreator.schema;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationSqlCommandStrategy;

public class BasicSchema extends SchemaCreator {

	@Override
	protected void schemaDefinition() {
		Table table = new Table();
		
		table.setName("CAROLINA");
		
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
		
		table.addColumn(nome);
		table.addColumn(idade);
		table.addColumn(address);
		
		table.setDesiredVersion("v3");
		table.setSchemaVersion("v1");
		
		MigrationSqlCommandStrategy migration1 = new MigrationSqlCommandStrategy();
		migration1.setNewVersion("v2");
		migration1.setOldVersion("v1");
		migration1.setSqlMigration("ALTER TABLE CAROLINA ADD COLUMN NAME VARCHAR(50)");
		
		MigrationSqlCommandStrategy migration2 = new MigrationSqlCommandStrategy();
		migration2.setNewVersion("v3");
		migration2.setOldVersion("v2");
		migration2.setSqlMigration("ALTER TABLE CAROLINA ADD COLUMN ADDRESS VARCHAR(100)");
		
		table.addMigrationStrategy(migration2);
		table.addMigrationStrategy(migration1);
		
		addTable(table);
	}

	@Override
	public String getSchemaName() {
		return "Basic Structural Scheme";
	}

}
