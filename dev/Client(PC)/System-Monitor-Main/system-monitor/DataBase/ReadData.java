package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import PCModel.ClassInfo;

public class ReadData {
	private static ReadData instance = null;

	private ReadData() {

	}

	public static ReadData getInstance() {
		if (instance == null)
			instance = new ReadData();
		return instance;
	}

	public ClassInfo readData() {
		String query = "SELECT * FROM classInfo";

		Connection connection = null;
		Statement statement = null;

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			System.out.println("JDBC를 찾지 못했습니다.");
			e.printStackTrace();
			return null;
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:classInfo.db");
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				String name = resultSet.getString("name");
				String classID = resultSet.getString("classID");
				int posR = resultSet.getInt("posR");
				int posC = resultSet.getInt("posC");

				ClassInfo classInfo = new ClassInfo(name, classID, posR, posC);
				resultSet.close();
				statement.close();
				connection.close();
				return classInfo;
				
			} else { // 데이터 없을 경우
				statement.close();
				connection.close();
				return null;
			}
		} catch (SQLException e) {
			System.out.println("JDBC 커넥션 에러!");
			e.printStackTrace();
			return null;
		}
	}
}
