package br.com.jq.syncthia.bdcreator;

import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.table.Table;
import br.com.jq.syncthia.bdcreator.table.migration.MigrationSqlCommandStrategy;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Table table = new Table();
		
		table.setTableName("CAROLINA");
		
		Column nome = new Column();
		nome.setName("NAME");
		nome.setSize(50);
		nome.setType("VARCHAR");
		Column idade = new Column();
		idade.setName("AGE");
		idade.setType("INT");
		
		table.addColumn(nome);
		table.addColumn(idade);
		
		table.setDesiredVersion("v2");
		table.setSchemaVersion("v1");
		
		MigrationSqlCommandStrategy migration1 = new MigrationSqlCommandStrategy();
		
		migration1.setNewVersion("v2");
		migration1.setOldVersion("v1");
		migration1.setSqlMigration("ALTER TABLE CAROLINA ADD COLUMN NAME VARCHAR(50)");
		
		table.addMigrationStrategy(migration1);
		
		table.createUnit();
		
		table.doMigrations();
	}
}
