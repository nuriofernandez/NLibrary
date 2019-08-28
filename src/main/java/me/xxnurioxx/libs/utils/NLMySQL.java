/**
 * 
 * N_Library by xXNurioXx [www.xxnurioxx.me]
 * Pack: [y17m12] Last Modify: 29/12/2017
 * Twitter: [https://twitter.com/xXNurioXx]
 * Mail: [personal@xxnurioxx.me]
 * 
 * @author xXNurioXx
 * @Target All
 */

package me.xxnurioxx.libs.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class NLMySQL {

	// Connection field
	private Connection connection;
	
	// Data fields
	private String host = "localhost";
	private String database = "DB";
	private String username = "user";
	private String password = "123";
	private int port = 3306;
	
	/**
	 * Auto connect to MySQL server when fired.
	 * @param host
	 * @param port
	 * @param database
	 * @param username
	 * @param password
	 */
	public NLMySQL(String host, int port, String database, String username, String password){
		this.host = host;
		this.password = password;
		this.username = username;
		this.password = database;
		this.port = port;
		connect();
	}
	
	/**
	 *  If connection loss do re-connect
	 */
	public void reConnect() {
		connect();
	}
	
	/**
	 *  Get connection field
	 * @return
	 */
	public Connection getConnection(){
		return connection;
	}
	
	/**
	 *  Verify if the connection is valid
	 * @return
	 */
	public boolean isValid(){
		try {
			return (connection != null && connection.isValid(300));
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 *  Connect to MySQL Server.
	 */
	private void connect() {
		try{
			if (connection != null && !connection.isClosed()) return;
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			if(!connection.isClosed())System.out.println("[MySQL] "+"Connected to '"+username+"'@'"+database+"'");
		}catch(Exception e){
			System.err.println("[MySQL] "+e.getMessage());
		}
	}

}
