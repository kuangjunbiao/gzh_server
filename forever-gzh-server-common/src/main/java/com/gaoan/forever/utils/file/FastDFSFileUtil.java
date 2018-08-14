package com.gaoan.forever.utils.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.config.ForeverConfig;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

/**
 * fastdfs 分布式文件系统 帮助类 Created by NO.9527 on 2017年7月20日
 */
@Component
public class FastDFSFileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FastDFSFileUtil.class);

	@Autowired
	private FastFileStorageClient fastFileStorageClient;

	@Autowired
	private ForeverConfig hzCustomConfig;

	private static FastDFSFileUtil self;

	private static final int poolSize = 20;
	/**
	 * 构建线程池 用做异步操作
	 */
	private static ExecutorService fastdfsThreadPool = Executors.newFixedThreadPool(poolSize);

	@PostConstruct
	public void init() {
		self = (FastDFSFileUtil) this;
	}

	/**
	 * 缩略图宽度
	 */
	@Value("${fdfs.thumbImage.width}")
	public static String Thumbnails_width;
	/**
	 * 缩略图高度
	 */
	@Value("${fdfs.thumbImage.height}")
	public static String Thumbnails_height;

	/**
	 * 上传文件
	 *
	 * @param file
	 *            文件对象
	 * @return 文件访问地址
	 * @throws IOException
	 */
	public static StorePath uploadFile(File file) throws Exception {
		StorePath storePath = null;
		FileInputStream fileInputStream = null;
		try {
			String suffix = FilenameUtils.getExtension(file.getName());
			if (!StringUtils.hasText(suffix)) {
				throw new AppException(MessageInfoConstant.IMAGE_TYPE_UNKNOWN);
			}
			fileInputStream = new FileInputStream(file);
			storePath = self.fastFileStorageClient.uploadFile(fileInputStream, file.length(), suffix, null);
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			logger.error("update File is error ", e);
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (file != null && file.exists()) {
				if (!file.delete()) {
					logger.error("delete File [{}] is error ", file.getAbsolutePath());
				}
			}
		}
		if (null == storePath) {
			throw new AppException(MessageInfoConstant.IMAGE_UPLAOD_FAIL);
		}
		return storePath;
	}

	/**
	 * 文件上传并创建缩略图
	 * 
	 * @param file
	 * @return
	 */
	public static StorePath uploadImageAndCrtThumbImage(MultipartFile file) {
		StorePath storePath = null;
		try {
			String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
			if (!StringUtils.hasText(suffix)) {
				throw new AppException(MessageInfoConstant.IMAGE_TYPE_UNKNOWN);
			}
			// 上传图片同时创建
			storePath = self.fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
					suffix, null);
		} catch (IOException e) {
			logger.error("图片上传异常！", e);
			throw new AppException(MessageInfoConstant.IMAGE_UPLAOD_FAIL);
		}
		if (storePath == null) {
			throw new AppException(MessageInfoConstant.IMAGE_UPLAOD_FAIL);
		}
		return storePath;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @return 文件访问地址
	 * @throws IOException
	 */
	public static StorePath uploadFile(MultipartFile file) throws Exception {
		StorePath storePath = null;
		String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!StringUtils.hasText(suffix)) {
			throw new AppException(MessageInfoConstant.IMAGE_TYPE_UNKNOWN);
		}
		try {
			storePath = self.fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == storePath) {
			throw new AppException(MessageInfoConstant.IMAGE_UPLAOD_FAIL);
		}
		return storePath;
	}

	/**
	 * 将一段字符串生成一个文件上传
	 * 
	 * @param content
	 *            文件内容
	 * @param fileExtension
	 * @return
	 */
	public static StorePath uploadFile(String content, String fileExtension) {
		StorePath storePath = null;
		try {
			byte[] buff = content.getBytes(Charset.forName("UTF-8"));
			ByteArrayInputStream stream = new ByteArrayInputStream(buff);
			storePath = self.fastFileStorageClient.uploadFile(stream, buff.length, fileExtension, null);
		} catch (Exception e) {
			logger.error("图片上传异常！", e);
			throw new AppException(MessageInfoConstant.IMAGE_UPLAOD_FAIL);
		}
		if (storePath == null) {
			throw new AppException(MessageInfoConstant.IMAGE_UPLAOD_FAIL);
		}
		return storePath;
	}

	public static void delDfsFile(String... filePaths) {
		fastdfsThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				for (String path : filePaths) {
					if (StringUtils.isEmpty(path))
						continue;
					self.fastFileStorageClient.deleteFile(path);
				}
			}
		});
	}

	// 封装文件/图片完整URL地址
	public static String getResAccessUrl(StorePath storePath) {
		// String fileUrl = self.hzCustomConfig.getDfsFileAccessPrefix() + "/" +
		// storePath.getFullPath();
		return "";
	}
}
