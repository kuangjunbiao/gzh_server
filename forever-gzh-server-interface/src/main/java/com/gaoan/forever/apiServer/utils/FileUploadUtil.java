package com.gaoan.forever.apiServer.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FileUploadUtil {

    public static List<String> upload(MultipartHttpServletRequest request, String savePath) throws Exception {

	List<String> files = new ArrayList<String>();
	// *************************开始文件上传*******************************

	// 创建一个通用的多部分解析器
	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
		request.getSession().getServletContext());
	// 判断 request 是否有文件上传,即多部分请求
	if (multipartResolver.isMultipart(request)) {
	    // 转换成多部分request
	    MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	    // 取得request中的所有文件名
	    Iterator<String> iter = multiRequest.getFileNames();

	    while (iter.hasNext()) {
		// 记录上传过程起始时的时间，用来计算上传时间
		int pre = (int) System.currentTimeMillis();
		// 取得上传文件
		MultipartFile file = multiRequest.getFile(iter.next());
		if (file != null) {
		    // 取得当前上传文件的文件名称
		    String myFileName = file.getOriginalFilename();
		    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
		    if (StringUtils.isNotBlank(myFileName.trim())) {
			System.out.println(myFileName);
			// 重命名上传后的文件名
			String fileName = "HeadImg" + myFileName.substring(myFileName.lastIndexOf("."));

			files.add(fileName);

			File tmp = new File(savePath);
			if (!tmp.exists()) {
			    tmp.mkdirs();
			}
			// 定义上传路径
			String path = savePath + "/" + fileName;
			File localFile = new File(path);
			file.transferTo(localFile);
		    }
		}
		// 记录上传该文件后的时间
		int finaltime = (int) System.currentTimeMillis();
		System.out.println(finaltime - pre);
	    }
	}
	// ************************结束文件上传***************************
	return files;
    }
}
