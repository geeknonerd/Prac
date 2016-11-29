package com.prac.geek.socket;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Scanner;

import com.prac.geek.model.File;
import com.prac.geek.model.User;
import com.prac.geek.service.FileService;
import com.prac.geek.util.CommandTransfer;

public class SocketClient {
	private static final String host = "localhost";
	private static final int port = 8800;
	private Socket mSocket = null;
	private Scanner scanner;

	public static final int SIZE = 4 * 1024;
	
	public SocketClient(){
		scanner = new Scanner(System.in);
	}

	public void showMain(){
		while(true){
			int item = selectItem();
			switch (item) {
			case 1:
				register();
				break;

			case 2:
				login();
				break;

			case 3:
				upload();
				break;

			case 4:
				System.out.println("Exit Success!");
				return;

			default:
				break;
			}
		}
	}

	private void register() {
		System.out.println("Please Input Username:");
		User user = new User();
		user.setUsername(scanner.next());
		System.out.println("Please Input Password:");
		String passwd = scanner.next();
		System.out.println("Confirm:");
		if (passwd.equals(scanner.next())) {
			user.setPassword(passwd);
			//			System.out.println("reg success! Username:"+user.getUsername()+","
			//					+"Password:"+user.getPassword());
			CommandTransfer ctf = new CommandTransfer();
			ctf.setCmd(CommandTransfer.CMD_REG);
			ctf.setData(user);
			ctf.setFlag(false);
			ctf.setResult(null);
			CommandTransfer connServer = connServer(ctf);
			if (connServer.isFlag()) {
				System.out.println("Register Success!");
			}else {
				System.out.println("Register Fail");
			}
		}else {
			System.out.println("password incorrect!");
		}
	}

	private void login() {
		System.out.println("Please Input Username:");
		User user = new User();
		user.setUsername(scanner.next());
		System.out.println("Please Input Password:");
		user.setPassword(scanner.next());
		CommandTransfer ctf = new CommandTransfer();
		ctf.setCmd(CommandTransfer.CMD_LOGIN);
		ctf.setData(user);
		ctf.setFlag(false);
		ctf.setResult(null);
		CommandTransfer connServer = connServer(ctf);
		if (connServer.isFlag()) {
			System.out.println("Login Success!");
		}else {
			System.out.println("Login Fail");
		}
	}

	private void upload() {
		System.out.println("Please Input File Path:");
		java.io.File jFile = new java.io.File(scanner.next());
		File file = null;
		BufferedInputStream bis = null;
		CommandTransfer ctf= null;
		try {
			file = new File();
			file.setFName(jFile.getName());
//			byte[] buf = new byte[1024*1024];
			//			ByteBuffer bb = ByteBuffer.allocate(4096);
//			StringBuilder sb = new StringBuilder();
			int bp = 0;
			bis = new BufferedInputStream(new FileInputStream(jFile));
//			while((bp=bis.read(buf))!=-1){
//				sb.append(new String(buf, 0, bp));
//			}
			byte[] buf = null;
			byte[] rs = new byte[SIZE];
			int times = 1;
			int op = 0;
			while(true){
				buf = new byte[times * SIZE];
				times++;
				if((bp = bis.read(buf))==-1) break;
//				System.out.println(bp+";"+(op+bp));
				rs = Arrays.copyOf(rs, op+bp);
				System.arraycopy(buf, 0, rs, op, bp);
				op += bp;
			}
			file.setFContent(rs);
			ctf= new CommandTransfer();
			ctf.setCmd(CommandTransfer.CMD_UPLOAD);
			ctf.setData(file);
			ctf.setFlag(false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (bis!=null) {
					bis.close();
					bis = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (ctf!=null) {
			CommandTransfer connServer = connServer(ctf);
			if (connServer.isFlag()) {
				System.out.println("Upload Success!");
			}else {
				System.out.println("Upload Fail!");
			}
		}
	}

	private int selectItem(){
		System.out.println("Welcome Geeker!");
		System.out.println("Please select item fllow:");
		System.out.println("1. Register");
		System.out.println("2. Login");
		System.out.println("3. Upload");
		System.out.println("4. Exit");

		return scanner.nextInt();
	}

	private CommandTransfer connServer(CommandTransfer ctf){
		OutputStream os = null;
		InputStream is = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			mSocket = new Socket(host, port);
			os = mSocket.getOutputStream();
			is = mSocket.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
			oos.writeObject(ctf);
			oos.flush();
			return (CommandTransfer) ois.readObject();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				if (oos!=null) {
					oos.close();
					oos=null;
				}
				if (ois!=null) {
					ois.close();
					ois = null;
				}
				if (is!=null) {
					is.close();
					is = null;
				}
				if (os!=null) {
					os.close();
					os = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
