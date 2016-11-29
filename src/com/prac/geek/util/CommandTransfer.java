package com.prac.geek.util;

import java.io.Serializable;

public class CommandTransfer implements Serializable{
	private static final long serialVersionUID = 982978978L;
	
	public static final String CMD_UPLOAD = "upload";
	public static final String CMD_REG = "reg";
	public static final String CMD_LOGIN = "login";
	
	public static final String RESULT_OK = "success";
	
	private String cmd;
	private Object data;
	private boolean flag;
	private String result;
	
	public CommandTransfer() {
		super();
	}

	public CommandTransfer(String cmd, boolean flag, String result) {
		super();
		this.cmd = cmd;
		this.flag = flag;
		this.result = result;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
