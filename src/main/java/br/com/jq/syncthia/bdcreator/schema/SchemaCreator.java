package br.com.jq.syncthia.bdcreator.schema;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jq.syncthia.bdcreator.schema.basicSchema.entity.RegisteredSchemaEntity;
import br.com.jq.syncthia.bdcreator.table.MigratableSelectable;

public abstract class SchemaCreator extends SchemaDefinitor {
	protected abstract void schemaDefinition();
	
	private List<SchemaPreProcessor> preProcessors;
	private List<SchemaProcessor> processors;
	private List<SchemaPostProcessor> postProcessors;
	
	public SchemaCreator() {
		preProcessors = new ArrayList<SchemaPreProcessor>();
		processors = new ArrayList<SchemaProcessor>();
		postProcessors = new ArrayList<SchemaPostProcessor>();
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
	
	private void abstractProcessIteration(List<? extends SchemaAbstractProcessor> abstractProcessorList) throws SQLException {
		for (SchemaAbstractProcessor abstractProcessor: abstractProcessorList) {
			abstractProcessor.processSchemaCreator(this);
		}
	}
	
	public void preProcessIteration() throws SQLException {
		abstractProcessIteration(preProcessors);
	}
	
	public void processIteration() throws SQLException {
		abstractProcessIteration(processors);
	}
	
	public void postProcessIteration() throws SQLException {
		abstractProcessIteration(postProcessors);
	}
	
	public void createSchema() throws SQLException {
		for (MigratableSelectable m: getMigratables()) {
			m.createUnit();
		}
	}
	
	public void migrateSchema() throws SQLException {
		for (MigratableSelectable m: getMigratables()) {
			m.doMigrations();
		}
	}
	
	@Override
	public String toString() {
		return "SchemaCreator [schemaName()=" + getName() + "]";
	}
	
	public boolean saveSchema() {
		boolean persisted = true;
		if (getConnection() != null) {
			RegisteredSchemaEntity entity = new RegisteredSchemaEntity();
			
			entity.setSchemaName(getName());
			entity.setSchemaVersion(getDesiredVersion());
			
			persisted = persisted && entity.persistEntity(getConnection());
		}
		
		
		for (MigratableSelectable m: getMigratables()) {
			persisted = persisted && m.saveMigratable();
		}
		
		return persisted;
	}

	@Override
	public void setConnection(Connection sqlConnection) {
		super.setConnection(sqlConnection);
		
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
	
	
}
