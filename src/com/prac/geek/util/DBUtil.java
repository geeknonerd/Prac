package com.prac.geek.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtil {
	private static final String driverClassName = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/prac";
	private static final String user = "root";
	private static final String password = "";

	public static Connection getConnection(){
		try {
			Class.forName(driverClassName);
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn){
		try {
			if (rs!=null) {
				rs.close();
				rs = null;
			}
			if (pstmt!=null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn!=null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
