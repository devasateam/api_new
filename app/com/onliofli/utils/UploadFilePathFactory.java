package com.onliofli.utils;

import java.io.File;

import play.Configuration;
import play.Logger;

public class UploadFilePathFactory {
	final static Configuration root = Configuration.root();

	public static String brandUploadPath() {
		String path="F:/images/";//root.getString("brand.images.upload.directory");//Play.application().configuration().getString("brand.images.upload.directory");//
		Logger.info("brand.images.upload.directory"+path);
		if(path!=null){
			File partialPath=new File(path);
			if(!partialPath.exists()){
				partialPath.mkdirs();
			}
		}
		if(!path.endsWith("/")){
			path+="/";
		}
		return path;
	}
}
