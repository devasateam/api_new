package com.onliofli.utils;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import play.Logger;

public class UploadFilePathFactory {
	final static Config root = ConfigFactory.load("fileupload.conf");

	public static String brandUploadPath() {
		String path = root.getString("brand.images.upload.directory");
		Logger.info("brand.images.upload.directory" + path);
		if (path != null) {
			File partialPath = new File(path);
			if (!partialPath.exists()) {
				partialPath.mkdirs();
			}
		}
		if (!path.endsWith("/")) {
			path += "/";
		}
		return path;
		
	}
}
