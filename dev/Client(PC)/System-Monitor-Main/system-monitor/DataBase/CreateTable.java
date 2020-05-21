package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	private static CreateTable instance = null;
	private CreateTable() {
		
	}
	public static CreateTable getInstance() {
		if(instance==null)
			instance = new CreateTable();
		return instance;
	}
	public boolean createTable() {
		String query = "CREATE TABLE IF NOT EXISTS classInfo (\n"
                + "	name text PRIMARY KEY,\n"
                + "	classID text NOT NULL,\n"
                + "	posR Integer,\n"
                + "	posC Integer\n"
                + ");";
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
		}
		catch(Exception e) {
			System.out.println("JDBC를 찾지 못했습니다.");
			e.printStackTrace();
			return false;
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:classInfo.db");
			statement = connection.createStatement();
			statement.execute(query);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("JDBC 커넥션 에러!");
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
