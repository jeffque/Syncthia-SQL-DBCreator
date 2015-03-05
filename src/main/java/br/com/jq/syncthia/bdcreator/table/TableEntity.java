package br.com.jq.syncthia.bdcreator.table;

import java.sql.Connection;
import br.com.jq.syncthia.bdcreator.annotations.GetAnnotation;
import br.com.jq.syncthia.bdcreator.exceptions.CantPersistAutomaticException;

public abstract class TableEntity {
	private GetAnnotation getAnnotation;
	
	public TableEntity() {
		getAnnotation = new GetAnnotation();
	}
	
	protected final boolean persistEntityInternal(Connection conn) throws CantPersistAutomaticException {
		Table t = getAnnotation.getRelatedTable(getClass());
		
		if (t == null) {
			throw new CantPersistAutomaticException();
		}
		
		return false;
	}
	
	protected boolean persistEntityManually(Connection conn) {
		return false;
	}
	
	public final boolean persistEntity(Connection conn) {
		try {
			return persistEntityInternal(conn);
		} catch (CantPersistAutomaticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return persistEntityManually(conn);
		}
	}
}
