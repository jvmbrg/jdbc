package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	
	private static Connection conn = null;
	
	//Metodo para fazer a conexão com o banco de dados, primeiro verificando se tem alguma conexão associada a varialvel de conexão conn
	public static Connection getConnection() {
		if(conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			}catch(SQLException e){
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	//Metodo estatico para carregar as propriedades definidas no arquivo db.properties
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		}catch(IOException e){
			throw new DbException(e.getMessage());
		}
	} 
	
	//Metodo para fechar a conexão com o banco de dados
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
