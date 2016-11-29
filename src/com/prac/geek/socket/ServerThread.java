package com.prac.geek.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.prac.geek.model.File;
import com.prac.geek.model.User;
import com.prac.geek.service.FileService;
import com.prac.geek.service.UserService;
import com.prac.geek.util.CommandTransfer;

public class ServerThread extends Thread{
	private FileService fService;
	private UserService uService;

	private Socket mSocket;

	public ServerThread(Socket socket) {
		mSocket = socket;
		fService = new FileService();
		uService = new UserService();
	}

	@Override
	public void run() {
		super.run();
		InputStream is = null;
		OutputStream os = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			is = mSocket.getInputStream();
			os = mSocket.getOutputStream();
			ois = new ObjectInputStream(is);
			oos = new ObjectOutputStream(os);
			CommandTransfer ctf = (CommandTransfer) ois.readObject();
//			System.out.println("Server:"+ctf.getCmd()+","+ctf.isFlag());
			CommandTransfer result = handleResult(ctf);
			oos.writeObject(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				if (oos!=null) {
					oos.close();
					oos = null;
				}
				if (ois!=null) {
					ois.close();
					ois = null;
				}
				if (os!=null) {
					os.close();
					os = null;
				}
				if (is!=null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private CommandTransfer handleResult(CommandTransfer ctf) {
		CommandTransfer rctf = null;
		switch (ctf.getCmd()) {
		case CommandTransfer.CMD_REG:
			User user = (User) ctf.getData();
			boolean rs = uService.register(user);
			System.out.println("Server reg:"+rs);
			rctf = new CommandTransfer(ctf.getCmd(),rs,CommandTransfer.RESULT_OK);
			break;
			
		case CommandTransfer.CMD_LOGIN:
			User user2 = (User) ctf.getData();
			boolean rs2 = uService.login(user2);
			System.out.println("Server login:"+rs2);
			rctf = new CommandTransfer(ctf.getCmd(),rs2,CommandTransfer.RESULT_OK);
			break;
			
		case CommandTransfer.CMD_UPLOAD:
			File file = (File) ctf.getData();
			boolean rs3 = fService.save(file);
			System.out.println("Server upload:"+rs3);
			rctf = new CommandTransfer(ctf.getCmd(),rs3,CommandTransfer.RESULT_OK);
			break;

		default:
			break;
		}
		return rctf;
	}
}
