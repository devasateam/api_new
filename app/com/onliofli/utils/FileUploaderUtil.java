package com.onliofli.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import play.Logger;

public class FileUploaderUtil {
	public static boolean uploadImageFromBase64(String imageString,	String newPath,String filename) {
		try {
			createFilePath(newPath);
			imageString = imageString.substring(imageString.indexOf(',') + 1).replace(" ", "+");
			byte[] imageByteArray = Base64.decodeBase64(imageString);
			FileOutputStream imageOutFile = new FileOutputStream(newPath+"/"+filename);
			imageOutFile.write(imageByteArray);
			imageOutFile.flush();
			imageOutFile.close();
			return true;

		} catch (IOException ioe) {
			Logger.error("Exception while reading the Image " + ioe);
		}
		return false;
	}

	public static File createFilePath(String path) {
		File partialPath = new File(path);
		if (!partialPath.exists()) {
			partialPath.mkdirs();
		}
		return partialPath;

	}
}
