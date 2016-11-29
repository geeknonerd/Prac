package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.prac.geek.socket.SocketClient;
import com.prac.geek.util.CommandTransfer;
import com.prac.geek.util.DBUtil;

public class Test {

	public static void main(String[] args){
//		test2();
	}
	
	static void test1() throws SQLException{
		Connection conn = DBUtil.getConnection();
		String sql = "select * from tb_user";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeQuery();
	}
	
//	static void test2(){
//		SocketClient client = new SocketClient();
//		CommandTransfer ctf = new CommandTransfer(CommandTransfer.CMD_REG, false, null);
//		CommandTransfer cctf = client.connServer(ctf);
//		System.out.println("Client:"+cctf.getCmd()+","+cctf.isFlag()+","+cctf.getResult());
//	}
}
