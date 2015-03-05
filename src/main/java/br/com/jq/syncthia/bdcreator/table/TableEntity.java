package br.com.jq.syncthia.bdcreator.table;

import br.com.jq.syncthia.bdcreator.annotations.GetAnnotation;
import br.com.jq.syncthia.bdcreator.exceptions.CantPersistAutomaticException;

public class TableEntity {
	private GetAnnotation getAnnotation;
	
	public TableEntity() {
		getAnnotation = new GetAnnotation();
	}
	
	protected final boolean persistEntityInternal() throws CantPersistAutomaticException {
		Table t = getAnnotation.getRelatedTable(getClass());
		
		if (t == null) {
			throw new CantPersistAutomaticException();
		}
		
		return false;
	}
	
	protected boolean persistEntityManually() {
		return false;
	}
	
	public final boolean persistEntity() {
		try {
			return persistEntityInternal();
		} catch (CantPersistAutomaticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return persistEntityManually();
		}
	}
}
