package com.gaoan.forever.utils.file;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by one on 2017/4/28.
 */
public class SysFileUtils {

	@Value("${transInterface.reconcile.serverUrl}")
	private String serverUrl;
	@Value("${transInterface.reconcile.serverDir}")
	private String serverDir;

	public SysFileUtils(String serverUrl, String serverDir){
		this.serverDir = serverDir;
		this.serverUrl = serverUrl;
	}

	public SysFileUtils(){
	}

	/**
	 * @param datas 数据
	 * @param dir 目录
	 * @param fileName 文件名
	 */
	public String writeFile(List<String> datas, String dir, String fileName, boolean append) {
		try {
			String absPath = serverUrl + serverDir + dir + "/" + fileName;
			absPath = absPath.replaceAll("\\\\", "/");
			File file = new File(absPath);
			FileUtils.writeLines(file, datas, append);
			return absPath;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String readFile(){


		return null;
	}

}
