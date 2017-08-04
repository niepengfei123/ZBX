package com.jy.jz.zbx.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 得到手机的一些状态和信息
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class FilePathUtil {

	public static final String APPROOT = "zbx/";
	public static final String IMAGE_TYPE = ".jpg";

	public static final String AUDIO_TYPE = ".amr";

	/**
	 * 返回SD卡的根路径
	 * @return
	 */	
	public static String getSDCardRoot() {
		Environment.getExternalStorageDirectory().getFreeSpace();
		Environment.getExternalStorageDirectory().getTotalSpace();
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/";
	}

	/**
	 * Check the SD card 
	 * @return
	 */
	public static boolean checkSDcardUsable() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 返回照片的路径
	 * @return
	 */
	public static String getPhotoFile() {
		String path = getSDCardRoot() + APPROOT + "img/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	
	/**
	 * 删除临时照片
	 * @return
	 */
	public static void clearPhotoFile(){
		String path = getSDCardRoot() + APPROOT + "img/";
		File file = new File(path);
		if (file.exists()) {
			File[] photoes = file.listFiles();
			for (File temp : photoes) {
				temp.delete();
			}
		}
	}

	public static String getVideoFile() {
		String path = getSDCardRoot() + APPROOT + "video/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}

	public static String getNormalFile() {
		String path = getSDCardRoot() + APPROOT + "file/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	
	
	public static String getCacheFile() {
		String path = getSDCardRoot() + APPROOT + "cache/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}

	/**
	 * 返回APP的根路径：（sd卡的根路径+app名）
	 * @return
	 */
	public static String getAppRoot() {
		return getSDCardRoot() + APPROOT;
	}
	
	
	/**
	 * 复制文件
	 * @return
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				FileInputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[2048];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.flush();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}
	}
	/**  
	  * 保存文件  
	  * @param bm  
	  * @param fileName  
	  * @throws IOException
	  */    
	public static File saveFile(Bitmap bm, String fileName) {
		String path = getPhotoFile() + "/header/";
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(path + fileName);
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myCaptureFile;
	}
}
