package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertData {
	private static InsertData instance = null;
	private InsertData() {
		
	}
	public static InsertData getInstance() {
		if(instance==null)
			instance = new InsertData();
		return instance;
	}
	public boolean insertData(String classID, int posR, int posC) {
		String name = classID + "_" + posR + "_" +posC;
		String query =  "INSERT INTO classInfo(name,classID,posR,posC) VALUES(?,?,?,?)";

		Connection connection = null;
		PreparedStatement statement = null;
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
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, classID);
			statement.setInt(3, posR);
			statement.setInt(4, posC);
			statement.executeUpdate();
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
