package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.BasicSchema;
import br.com.jq.syncthia.bdcreator.schema.basicSchema.processor.*;
import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public class SchemaCollection extends SchemaCollectionInternal {
	private SchemaCollection rootCollection;
	
	private List<SchemaCreator> registeredSchemas;
	
	private List<SchemaPreProcessor> preProcessors;
	private List<SchemaProcessor> processors;
	private List<SchemaPostProcessor> postProcessors;
	
	public SchemaCollection() {
		setRootCollection(this);
		registeredSchemas = new ArrayList<SchemaCreator>();
		
		preProcessors = new ArrayList<SchemaPreProcessor>();
		processors = new ArrayList<SchemaProcessor>();
		postProcessors = new ArrayList<SchemaPostProcessor>();
		
		registerSchema(new BasicSchema());
		
		addPreProcessor(new RunSchemaPreProcessors());
		
		addProcessor(new RunSchemaProcessors());
		addProcessor(new SchemaCreatorProcessor());
		addProcessor(new SchemaMigratorProcessor());
		
		addPostProcessor(new SchemaSaveProcessor());
		addPostProcessor(new RunSchemaPostProcessors());
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
		schema.setSchemaCollection(getRootCollection());
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
	
	private void abstractProcessIteration(List<? extends SchemaAbstractProcessor> abstractProcessorList) throws SQLException {
		for (SchemaAbstractProcessor abstractProcessor: abstractProcessorList) {
			for (SchemaCreator schema: registeredSchemas) {
				abstractProcessor.processSchemaCreator(schema);
			}
		}
	}
	
	private void preProcessIteration() throws SQLException {
		abstractProcessIteration(preProcessors);
	}
	
	private void processIteration() throws SQLException {
		abstractProcessIteration(processors);
	}
	
	private void postProcessIteration() throws SQLException {
		abstractProcessIteration(postProcessors);
	}

	public final void createOrMigrateSchema() throws SQLException {
		schemaMetaDataFromExisting();
		
		preProcessIteration();
		processIteration();	
		postProcessIteration();
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
	
	public SchemaCollection getRootCollection() {
		return rootCollection;
	}

	public void setRootCollection(SchemaCollection rootCollection) {
		this.rootCollection = rootCollection;
	}
	
}
