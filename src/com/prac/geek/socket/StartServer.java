package com.prac.geek.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer {

	public static void main(String[] args) {
		ServerSocket server=null;
		try {
			server = new ServerSocket(8800);
			System.out.println("----------Server start---------");
			while(true){
				Socket socket = server.accept();
				new ServerThread(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (server!=null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
