package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.BasicSchema;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.SchemaCreatorProcessor;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.SchemaMigratorProcessor;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.SchemaSaveProcessor;
import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public class SchemaCollection extends SchemaCollectionInternal {
	private List<SchemaCreator> registeredSchemas;
	private List<SchemaPreProcessor> preProcessors;
	private List<SchemaProcessor> processors;
	private List<SchemaPostProcessor> postProcessors;
	
	public SchemaCollection() {
		registeredSchemas = new ArrayList<SchemaCreator>();
		
		preProcessors = new ArrayList<SchemaPreProcessor>();
		processors = new ArrayList<SchemaProcessor>();
		postProcessors = new ArrayList<SchemaPostProcessor>();
		
		registerSchema(new BasicSchema());
		addProcessor(new SchemaCreatorProcessor());
		addProcessor(new SchemaMigratorProcessor());
		addPostProcessor(new SchemaSaveProcessor());
	}
	
	public void addPreProcessor(SchemaPreProcessor preProcessor) {
		preProcessors.add(preProcessor);
	}
	
	public void addProcessor(SchemaProcessor processor) {
		processors.add(processor);
	}
	
	public void addPostProcessor(SchemaPostProcessor postProcessor) {
		postProcessors.add(postProcessor);
	}
	
	@Override
	public void setConnection(Connection sqlConnection) {
		super.setConnection(sqlConnection);
		
		for (SchemaCreator schema: registeredSchemas) {
			schema.setConnection(sqlConnection);
		}
		
		for (SchemaAbstractProcessor processor: preProcessors) {
			processor.setConnection(sqlConnection);
		}
		
		for (SchemaAbstractProcessor processor: processors) {
			processor.setConnection(sqlConnection);
		}
		
		for (SchemaAbstractProcessor processor: postProcessors) {
			processor.setConnection(sqlConnection);
		}
	}
	
	public void registerSchema(SchemaCreator schema) {
		super.registerDefinitor(schema);
		
		registeredSchemas.add(schema);
		schema.schemaDefinition();
		schema.setConnection(getConnection());
	}
	
	private void schemaMetaDataFromExisting() {
		ExistingSchemaCollection existingCollection = new ExistingSchemaCollection(getConnection());
		
		for (ExistingSchema existingSchema: existingCollection.getExistingSchemas()) {
			SchemaCreator counterPartSchema = getSchema(existingSchema.getName());
			
			// It happens when there is a registered schema not in the present schema collection
			if (counterPartSchema == null) {
				continue;
			}
			for (MigratableSelectable existingMigratable: existingSchema.getMigratables()) {
				MigratableSelectable counterPartMigratable = counterPartSchema.getMigratable(existingMigratable.getName());
				
				// It happens when there is a metadata from a table but it is not in the schema anymore
				if (counterPartMigratable == null) {
					continue;
				}
				counterPartMigratable.setRegisteredVersion(existingMigratable.getRegisteredVersion());
			}
		}
	}
	
	public final void createOrMigrateSchema() throws SQLException {
		schemaMetaDataFromExisting();
		
		for (SchemaPreProcessor preProcessor: preProcessors) {
			for (SchemaCreator schema: registeredSchemas) {
				preProcessor.processSchemaCreator(schema);
			}
		}
		
		for (SchemaProcessor processor: processors) {
			for (SchemaCreator schema: registeredSchemas) {
				processor.processSchemaCreator(schema);
			}
		}
				
		for (SchemaPostProcessor postProcessor: postProcessors) {
			for (SchemaCreator schema: registeredSchemas) {
				postProcessor.processSchemaCreator(schema);
			}
		}
	}
	
	public List<SchemaCreator> getRegisteredSchemas() {
		return registeredSchemas;
	}
	
	public SchemaCreator getSchema(String schemaName) {
		for (SchemaCreator s: registeredSchemas) {
			if (schemaName.equals(s.getName())) {
				return s;
			}
		}
		
		return null;
	}
	
}
