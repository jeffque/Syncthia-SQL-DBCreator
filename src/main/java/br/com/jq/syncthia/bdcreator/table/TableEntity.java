package br.com.jq.syncthia.bdcreator.table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.jq.syncthia.bdcreator.annotations.GetAnnotation;
import br.com.jq.syncthia.bdcreator.column.Column;
import br.com.jq.syncthia.bdcreator.columnset.TableKey;
import br.com.jq.syncthia.bdcreator.exceptions.CantPersistAutomaticException;
import br.com.jq.syncthia.bdcreator.exceptions.NoColumnToPersistAutomaticException;
import br.com.jq.syncthia.bdcreator.exceptions.NoTableToPersistAutomaticException;
import br.com.jq.syncthia.bdcreator.exceptions.NoUniqueKeyToPersistAutomaticException;
import br.com.jq.syncthia.bdcreator.schema.SchemaCollection;

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
	
	private void setParams(PreparedStatement pStmt, List<Column> columns, int pStmtOffset) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		for (int i = columns.size() - 1; i >= 0; i--) {
			int pStmtPos = i + pStmtOffset;
			Column col = columns.get(i);
			setParamPStmt(pStmt, pStmtPos, col);
		}
	}
	
	protected final boolean persistEntityInternal(SchemaCollection schemaCollection) throws CantPersistAutomaticException {
		Table t = getAnnotation.getRelatedTable(getClass(), schemaCollection);
		
		if (t == null) {
			throw new NoTableToPersistAutomaticException();
		}
		
		List<Column> columns = getAnnotation.getColumns(getClass(), t);
		
		if (columns == null || columns.size() == 0) {
			throw new NoColumnToPersistAutomaticException();
		}
		
		TableKey uniqueKey = getAnnotation.getUniqueKey(getClass(), t);
		
		if (uniqueKey == null) {
			throw new NoUniqueKeyToPersistAutomaticException();
		}
		
		int updatedRows = 0;
		
		try {
			int pStmtOffset;
			
			PreparedStatement updateStmt = t.prepareUpdateStatement(uniqueKey, columns);
			pStmtOffset = 1;
			setParams(updateStmt, columns, pStmtOffset);
			
			pStmtOffset = columns.size() + 1;
			setParams(updateStmt, uniqueKey.getColumns(), pStmtOffset);
			updatedRows = updateStmt.executeUpdate();
			
			if (!t.getCachePreparedStmt()) {
				updateStmt.close();
			}
			
			if (updatedRows == 0) {
				PreparedStatement insertStmt = t.prepareInsertStatement(columns);
				pStmtOffset = 1;
				setParams(insertStmt, columns, pStmtOffset);
				
				updatedRows = insertStmt.executeUpdate();
				if (!t.getCachePreparedStmt()) {
					insertStmt.close();
				}
				
				autoSetAIPKCol(schemaCollection);
			}
		} catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return updatedRows != 0;
	}
	
	public void autoSetAIPKCol(SchemaCollection schemaCollection) throws CantPersistAutomaticException {
		Table t = getAnnotation.getRelatedTable(getClass(), schemaCollection);
		
		if (t == null) {
			throw new NoTableToPersistAutomaticException();
		}
		
		List<Column> columns = getAnnotation.getColumns(getClass(), t);
		
		if (columns == null || columns.size() == 0) {
			throw new NoColumnToPersistAutomaticException();
		}
		
		TableKey uniqueKey = getAnnotation.getUniqueKey(getClass(), t);
		
		if (uniqueKey == null) {
			throw new NoUniqueKeyToPersistAutomaticException();
		}
		
		try {
			Method aipkSetter = getAnnotation.getAIPKSetter(getClass(), t);
			Column aipkCol = getAnnotation.getAIPKCol(getClass(), t);
			if (aipkSetter != null && aipkCol != null) {
				PreparedStatement selectStmt = t.prepareSelectStatement(uniqueKey);
				int pStmtOffset = 1;
				setParams(selectStmt, uniqueKey.getColumns(), pStmtOffset);
				
				ResultSet rsaipk = selectStmt.executeQuery();
				
				int aipkValue = rsaipk.getInt(aipkCol.getName());
				rsaipk.close();
				
				if (!t.getCachePreparedStmt()) {
					selectStmt.close();
				}
				
				aipkSetter.invoke(this, aipkValue);
			}
		} catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean persistEntityManually(SchemaCollection schemaCollection) {
		System.out.println("olá? " + this.getClass());
		return false;
	}
	
	public boolean persistEntity(SchemaCollection schemaCollection) {
		try {
			return persistEntityInternal(schemaCollection);
		} catch (CantPersistAutomaticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return persistEntityManually(schemaCollection);
		}
	}
	
	protected final boolean removeEntityInternal(SchemaCollection schemaCollection) throws CantPersistAutomaticException {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected boolean removeEntityManually(SchemaCollection schemaCollection) {
		return false;
	}
	
	public boolean removeEntity(SchemaCollection schemaCollection) {
		try {
			return removeEntityInternal(schemaCollection);
		} catch (CantPersistAutomaticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return removeEntityManually(schemaCollection);
		}
	}
}
