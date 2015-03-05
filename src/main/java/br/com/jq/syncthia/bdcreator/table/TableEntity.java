package br.com.jq.syncthia.bdcreator.table;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		Field f = getAnnotation.getFieldFromColumn(getClass(), col.getName());
		
		if (f.get(this) != null) {
			switch (col.getType().toLowerCase()) {
			case "int":
				pStmt.setInt(pStmtPos, f.getInt(this));
				break;
			case "varchar":
			case "char":
			case "string":
			default:
				pStmt.setString(pStmtPos, f.get(this).toString());
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
		
		Column[] columns = getAnnotation.getColumns(getClass(), t);
		
		if (columns == null || columns.length == 0) {
			throw new CantPersistAutomaticException(); //XXX create exception for FindColumns
		}
		
		TableKey uniqueKey = getAnnotation.getUniqueKey(getClass(), t);
		
		if (uniqueKey == null) {
			throw new CantPersistAutomaticException(); //XXX create exception for FindUniqueKey
		}
		
		boolean firstCol;
		
		StringBuilder updateSql = new StringBuilder("UPDATE ").append(t.getName()).append(" SET ");
		
		firstCol = true;
		for (Column col: columns) {
			if (!firstCol) {
				updateSql.append(", ");
			} else {
				firstCol = false;
			}
			updateSql.append(col.getName()).append("= ?");
		}
		
		updateSql.append(" WHERE ");
		
		firstCol = true;
		for (Column col: uniqueKey.getColumns()) {
			if (!firstCol) {
				updateSql.append(" AND ");
			} else {
				firstCol = false;
			}
			updateSql.append(col.getName()).append("= ?");
		}
		
		StringBuilder insertSql = new StringBuilder("INSERT INTO ").append(t.getName()).append(" (");
		firstCol = true;
		for (Column col: columns) {
			if (!firstCol) {
				insertSql.append(", ");
			} else {
				firstCol = false;
			}
			insertSql.append(col.getName());
		}
		insertSql.append(") VALUES (");
		firstCol = true;
		for (int i = columns.length - 1; i >= 0; i--) {
			if (!firstCol) {
				insertSql.append(", ");
			} else {
				firstCol = false;
			}
			insertSql.append("?");
		}
		insertSql.append(")");
		
		int updatedRows = 0;
		
		try {
			int pStmtOffset;
			
			PreparedStatement updateStmt = conn.prepareStatement(updateSql.toString());
			pStmtOffset = 1;
			for (int i = columns.length - 1; i >= 0; i--) {
				int pStmtPos = i + pStmtOffset;
				Column col = columns[i];
				setParamPStmt(updateStmt, pStmtPos, col);
			}
			
			pStmtOffset = columns.length + 1;
			for (int i = uniqueKey.getColumns().size() - 1; i >= 0; i--) {
				int pStmtPos = i + pStmtOffset;
				Column col = uniqueKey.getColumns().get(i);
				setParamPStmt(updateStmt, pStmtPos, col);
			}
			updatedRows = updateStmt.executeUpdate();
			
			if (updatedRows == 0) {
				PreparedStatement insertStmt = conn.prepareStatement(insertSql.toString());
				pStmtOffset = 1;
				for (int i = columns.length - 1; i >= 0; i--) {
					int pStmtPos = i + pStmtOffset;
					Column col = columns[i];
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
