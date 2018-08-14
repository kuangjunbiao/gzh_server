package com.gaoan.forever.base;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.Message;
import com.gaoan.forever.model.Message.Builder;
import com.gaoan.forever.utils.LogUtil;

@Configuration
public class ExceptionHandler implements HandlerExceptionResolver {

	@Autowired
	private MessageSource messageSource;

	private final static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (null != ex) {
			ex = adaptException(ex, request);
			String httpAjax = request.getHeader("X-Requested-With");

			if (httpAjax != null && "XMLHttpRequest".equals(httpAjax)) {
				return handlerAjax(ex, request, response);
			} else {
				return handlerDefault(request, response, ex);
			}
		}
		return null;
	}

	/**
	 * 适配异常类型
	 *
	 * @param ex
	 * @param request
	 * @return
	 */
	private AppException adaptException(Exception ex, HttpServletRequest request) {

		if (ex instanceof AuthorizationException) {
			log.warn(LogUtil.getLogStr(request), ex);
			return new AppException(MessageInfoConstant.URL_UNAUTHORIZED, ex);
		}

		if (ex instanceof MultipartException) {
			log.error(LogUtil.getLogStr(request), ex);
			Throwable e = ex.getCause();
			if (e instanceof IllegalStateException) {
				e = e.getCause();
				if (e instanceof FileUploadBase.SizeLimitExceededException
						|| e instanceof FileUploadBase.FileSizeLimitExceededException) {
					return new AppException(MessageInfoConstant.UPLOAD_FILE_SIZE_OVER_LIMITED, ex);
				}
			}
			return new AppException(MessageInfoConstant.NONE, ex);
		}
		if (!(ex instanceof AppException)) {
			log.error(LogUtil.getLogStr(request), ex);
			return new AppException(MessageInfoConstant.NONE, ex);
		}

		AppException appException = (AppException) ex;
		if (null != appException.getException()) {
			log.warn(LogUtil.getLogStr(request), appException);
		} else {
			log.warn(appException.getMessage() + LogUtil.getLogStr(request));
		}

		return appException;
	}

	/**
	 * ajax异常处理器
	 *
	 * @param ex
	 * @param request
	 * @param response
	 * @return
	 */
	private ModelAndView handlerAjax(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		Locale locale = LocaleContextHolder.getLocale();
		Map<String, Object> map = ((AppException) ex).getExceptionMap();
		Builder builder = Message.newBuilder();
		builder.setRet(String.valueOf(map.get("ret")));
		builder.setCode(String.valueOf(map.get("ret")));
		if (!(boolean) map.get("iscustommsg")) {
			builder.setMsg(messageSource.getMessage(String.valueOf(map.get("ret")), null, locale));
		} else {
			builder.setMsg(String.valueOf(map.get("msg")));
		}

		PrintWriter out = null;
		try {
			response.setContentType("application/json; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(200);
			out = response.getWriter();
			out.println(JSON.toJSONString(builder.build()));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
		return new ModelAndView();
	}

	/**
	 * 普通异常处理器
	 *
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	private ModelAndView handlerDefault(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		Locale locale = LocaleContextHolder.getLocale();
		Map<String, Object> map = ((AppException) ex).getExceptionMap();
		Builder builder = Message.newBuilder();
		builder.setRet(String.valueOf(map.get("ret")));
		builder.setCode(String.valueOf(map.get("ret")));
		if (!(boolean) map.get("iscustommsg")) {
			builder.setMsg(messageSource.getMessage(String.valueOf(map.get("ret")), null, locale));
		} else {
			builder.setMsg(String.valueOf(map.get("msg")));
		}

		PrintWriter out = null;
		try {
			response.setContentType("application/json; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(200);
			out = response.getWriter();
			out.println(JSON.toJSONString(builder.build()));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}

		return new ModelAndView();
	}

	private void handleJsonError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		if (response.isCommitted()) {
			return;
		}

		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(200);

		StringBuilder buf = new StringBuilder();
		String ret;
		String msg;
		boolean iscustommsg = false;
		Message.Builder builder = Message.newBuilder();
		if (ex instanceof AppException) {
			AppException exception = (AppException) ex;
			ret = exception.getRet();
			msg = exception.getMessage();
			iscustommsg = exception.isIscustommsg();
		} else {
			ret = MessageInfoConstant.COMMON_ERROR_CODE.getCode().toString();
			msg = MessageInfoConstant.COMMON_ERROR_CODE.getMsgZh();

		}
		Locale locale = LocaleContextHolder.getLocale();
		builder.setRet(ret);
		if (!iscustommsg) {
			builder.setMsg(messageSource.getMessage(String.valueOf(ret), null, locale));
		} else {
			builder.setMsg(msg);
		}

		String json = JSON.toJSONString(builder.build());
		buf.append(json);
		try {
			OutputStream out = response.getOutputStream();
			out.write(buf.toString().getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void handlerAPIError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		handleJsonError(request, response, ex);
	}
}
