package br.com.jq.syncthia.bdcreator.interfaces;

import java.sql.Connection;

/**
 * Guarantees that this guy uses a given connection. Also, if it has some
 * connectable element inside it, then this element should have the same
 * connection as it has, be added before or after the connection edition.
 * 
 * @author jeffque
 *
 */
public interface Connectable {
	public void setConnection(Connection sqlConnection);
	
	public Connection getConnection();
}
