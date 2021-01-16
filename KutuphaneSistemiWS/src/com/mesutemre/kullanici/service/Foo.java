package com.mesutemre.kullanici.service;

import java.io.File;

import com.mesutemre.util.KutuphaneSistemiUtil;

public class Foo {

	public static void main(String[] args) {
		File folder = new File(KutuphaneSistemiUtil.getUploadProfilResmiPath());
		listFilesForFolder(folder);
	}
	
	public static void listFilesForFolder(File folder) {
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	System.out.println(fileEntry.getAbsolutePath());
	            
	        }
	    }
	}

}
