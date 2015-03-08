package br.com.jq.syncthia.bdcreator.table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.jq.syncthia.bdcreator.annotations.GetAnnotation;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.exceptions.CantPersistAutomaticException;

public abstract class TableEntity {
	private GetAnnotation getAnnotation;
	
	public TableEntity() {
		getAnnotation = new GetAnnotation();
	}
	
	private void setParamPStmt(PreparedStatement pStmt, int pStmtPos, Column col) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		Method m = getAnnotation.getGetterFromColumn(getClass(), col.getName());
		Object returnGot = m.invoke(this);
		
		if (returnGot != null) {
			switch (col.getType().toLowerCase()) {
			case "int":
				pStmt.setInt(pStmtPos, (Integer)returnGot);
				break;
			case "varchar":
			case "char":
			case "string":
			default:
				pStmt.setString(pStmtPos, returnGot.toString());
			}
		} else {
			pStmt.setNull(pStmtPos, 0);
		}
	}
	
	protected final boolean persistEntityInternal(Connection conn) throws CantPersistAutomaticException {
		Table t = getAnnotation.getRelatedTable(getClass());
		
		if (t == null) {
			throw new CantPersistAutomaticException(); //XXX create exception for FindTable
		}
		
		List<Column> columns = getAnnotation.getColumns(getClass(), t);
		
		if (columns == null || columns.size() == 0) {
			throw new CantPersistAutomaticException(); //XXX create exception for FindColumns
		}
		
		TableKey uniqueKey = getAnnotation.getUniqueKey(getClass(), t);
		
		if (uniqueKey == null) {
			throw new CantPersistAutomaticException(); //XXX create exception for FindUniqueKey
		}
		
		int updatedRows = 0;
		t.setConnection(conn);
		
		try {
			int pStmtOffset;
			
			PreparedStatement updateStmt = t.prepareUpdateStatement(uniqueKey, columns);
			pStmtOffset = 1;
			for (int i = columns.size() - 1; i >= 0; i--) {
				int pStmtPos = i + pStmtOffset;
				Column col = columns.get(i);
				setParamPStmt(updateStmt, pStmtPos, col);
			}
			
			pStmtOffset = columns.size() + 1;
			for (int i = uniqueKey.getColumns().size() - 1; i >= 0; i--) {
				int pStmtPos = i + pStmtOffset;
				Column col = uniqueKey.getColumns().get(i);
				setParamPStmt(updateStmt, pStmtPos, col);
			}
			updatedRows = updateStmt.executeUpdate();
			
			if (updatedRows == 0) {
				PreparedStatement insertStmt = t.prepareInsertStatement(columns);
				pStmtOffset = 1;
				for (int i = columns.size() - 1; i >= 0; i--) {
					int pStmtPos = i + pStmtOffset;
					Column col = columns.get(i);
					setParamPStmt(insertStmt, pStmtPos, col);
				}
				updatedRows = insertStmt.executeUpdate();
			}
		} catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return updatedRows != 0;
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
