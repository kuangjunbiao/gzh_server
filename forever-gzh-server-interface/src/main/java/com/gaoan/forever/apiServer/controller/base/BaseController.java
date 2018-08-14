package com.gaoan.forever.apiServer.controller.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.Message;
import com.gaoan.forever.utils.cache.CacheUtils;
import com.gaoan.forever.utils.file.FastDFSFileUtil;
import com.github.tobato.fastdfs.domain.StorePath;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by NO.9527 on 2017年7月15日
 */
@RestController
@RequestMapping(value = "/api")
@Api(value = "BaseController", description = "Base控制器")
public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@ApiOperation(value = "后台-未授权")
	@RequestMapping(value = "/unauthorized")
	@ApiIgnore
	@ResponseBody
	public Object unauthorized(HttpServletRequest request) throws Exception {
		logger.debug("URL请求未授权！");
		throw new AppException(MessageInfoConstant.URL_UNAUTHORIZED);
	}

	@ApiOperation(value = "后台-文件上传")
	@RequestMapping(value = "/uploadFile", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object uploadFile(HttpServletRequest request,
			@ApiParam("文件") @RequestParam(required = true) MultipartFile file) throws Exception {
		StorePath sp = FastDFSFileUtil.uploadFile(file);
		Message.Builder builder = Message.newBuilder();
		builder.put("url", FastDFSFileUtil.getResAccessUrl(sp));
		return builder.builldJson();
	}

	@ApiOperation(value = "后台-图片上传并创建缩略图")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "isThumbnails", value = "是否创建缩略图0:不创建，1：创建", paramType = "query", dataType = "int", required = false), })
	@RequestMapping(value = "/uploadImage", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object uploadImage(HttpServletRequest request,
			@ApiParam("文件") @RequestParam(required = true) MultipartFile file,
			@RequestParam(required = false, defaultValue = "") int isThumb) throws Exception {
		StorePath sp = isThumb == 0 ? FastDFSFileUtil.uploadFile(file)
				: FastDFSFileUtil.uploadImageAndCrtThumbImage(file);

		String extension = "." + FilenameUtils.getExtension(sp.getFullPath());
		String thumbnailsSize = "_" + FastDFSFileUtil.Thumbnails_width + "x" + FastDFSFileUtil.Thumbnails_height
				+ extension;
		String url = FastDFSFileUtil.getResAccessUrl(sp);
		String thumbnailsUrl = url.substring(0, url.lastIndexOf(extension)) + thumbnailsSize;

		Message.Builder builder = Message.newBuilder();
		builder.put("url", url);
		builder.put("thumbnailsUrl", thumbnailsUrl);
		return builder.builldJson();
	}

	@ApiOperation(value = "后台-获得省市县信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "类型：0：省(省级市)列表，1：获得市县列表", paramType = "query", dataType = "int", required = true),
			@ApiImplicitParam(name = "pid", value = "省份id,当type等于1时 pid不能为空", paramType = "query", dataType = "String", required = false), })
	@RequestMapping(value = "/getAreas", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object getAreas(HttpServletRequest request, @RequestParam(required = true) int type,
			@RequestParam(required = false, defaultValue = "") String pid) throws Exception {
		if (type != 0 && type != 1) {
			throw new AppException(MessageInfoConstant.WEBNOTIFY_TYPE_PARAM_ERROR);
		}
		// int level_country = 1;//国家
		int id_country = 1;
		int level_province = 2;// 省
		int level_city = 3;// 市
		//// 级别 1：国家 2:省和省级市 3：县市
		Integer level = level_province;
		if (type == 0) {
			level = level_province;
			pid = String.valueOf(id_country);
		} else if (type == 1) {
			level = level_city;
			if (StringUtils.isBlank(pid)) {
				throw new AppException(MessageInfoConstant.WEBNOTIFY_TYPE_PARAM_ERROR);
			}
		}

		Message.Builder builder = Message.newBuilder();
		return builder.builldJson();
	}

	@ApiOperation(value = "前台-获取银行类型列表")
	@RequestMapping(value = "/bank_list", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object list(HttpServletRequest request) throws Exception {
		// PageInfo<Map<String, Object>> pageInfo =
		// bankTypeService.selectByPage(null, banklist_page,
		// banklist_page_size);
		// return pageInfo.getList();
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<Object> processAreas(String key) {
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> areas = (Map<String, Object>) CacheUtils.getHashOps(key);
		for (Map.Entry<String, Object> area : areas.entrySet()) {
			result.add(area.getValue());
		}
		return result;
	}

	@ApiOperation(value = "前台-服务暂停")
	@RequestMapping(value = "/serverStop", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object serverStop(HttpServletRequest request) throws Exception {
		logger.info("服务暂停，升级或维护中");
		throw new AppException(MessageInfoConstant.STOP_SERVER);
	}

	@ApiOperation(value = "获得有效币种列表")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/useingCoins", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object useingCoins(HttpServletRequest request) throws Exception {
		logger.info("start to search ... ");
		/*
		 * TbCurrTypeEntity entity = new TbCurrTypeEntity();
		 * entity.setStatus(1); List<TbCurrTypeEntity> coins =
		 * currTypeService.queryAll(entity); Message.Builder builder =
		 * Message.newBuilder(); builder.put("list", coins); return
		 * builder.builldJson();
		 */
		return null;
	}

}
