package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {
	
	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
		}
		catch(Exception e) {
			System.out.println("JDBC를 찾지 못했습니다.");
		}
		/*String query = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";*/
		//String query =  "INSERT INTO warehouses(name,capacity) VALUES(?,?)";
		String query = "SELECT * FROM warehouses";
		connection = DriverManager.getConnection("jdbc:sqlite:test.db");
		//PreparedStatement pstmt = connection.prepareStatement(query);
		statement = connection.createStatement();
		//statement.execute(query);
		//pstmt.setString(1, "Damin");
        //pstmt.setDouble(2, 1.4);
        //pstmt.executeUpdate();
		ResultSet resultSet = statement.executeQuery(query);
		/*while(resultSet.next()) {
			System.out.println(resultSet.getString("name"));
			System.out.println(resultSet.getDate("capacity"));
		}*/
		if(resultSet.next()) {
			System.out.println(resultSet.getString("name"));
			System.out.println(resultSet.getDate("capacity"));
			System.out.println("하잇");
			
		}
		while(resultSet.next()) {
			System.out.println(resultSet.getString("name"));
			System.out.println(resultSet.getDate("capacity"));
		}
		resultSet.close();
		connection.close();
	}
}
