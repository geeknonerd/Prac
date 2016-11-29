package com.prac.geek.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.prac.geek.model.User;
import com.prac.geek.util.DBUtil;

public class UserService {
	
	public UserService() {
	}
	
	public boolean register(User user){
		Connection conn = DBUtil.getConnection();
		String sql = "insert into tb_user(username,password) values(?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(null, pstmt, conn);
		}
		return false;
	}
	
	public boolean login(User user){
		Connection conn = DBUtil.getConnection();
		String sql = "select * from tb_user where username=? and password=?";
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			resultSet = pstmt.executeQuery();
			boolean rs = false;
			if (resultSet!=null&&resultSet.next()) {
				rs = true;
			}
//			System.out.println("login:"+rs);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(resultSet, pstmt, conn);
		}
		return false;
	}

}
