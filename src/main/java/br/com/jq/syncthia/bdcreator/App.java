package br.com.jq.syncthia.bdcreator;

import br.com.jq.syncthia.bdcreator.schema.SchemaCreator;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		SchemaCreator.createOrMigrateSchema();
	}
}
