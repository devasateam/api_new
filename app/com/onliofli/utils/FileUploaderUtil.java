package com.onliofli.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import play.Logger;

public class FileUploaderUtil {
	public static boolean uploadImageFromBase64(String imageString,
			String newPath) {
		boolean isDone = false;
		try {
			imageString = imageString.substring(imageString.indexOf(',') + 1);// .replace(" ",
																				// "+");
			byte[] imageByteArray = Base64.decodeBase64(imageString);
			FileOutputStream imageOutFile = new FileOutputStream(newPath);
			imageOutFile.write(imageByteArray);
			imageOutFile.flush();
			imageOutFile.close();
			isDone = true;

		} catch (IOException ioe) {
			Logger.error("Exception while reading the Image " + ioe);
		}
		return isDone;
	}
}
