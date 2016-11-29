package com.prac.geek.service;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;

import com.prac.geek.model.File;
import com.prac.geek.util.DBUtil;

public class FileService {
	
	public FileService() {
	}
	
	public boolean save(File file){
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		boolean rs = false;
		try {
			fos = new FileOutputStream(file.getFName());
			bos = new BufferedOutputStream(fos);
			bos.write(file.getFContent());
			bos.flush();
			rs = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (bos!=null) {
					bos.close();
					bos = null;
				}
				if (fos!=null) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	
}
