package com.gaoan.forever.utils;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.gaoan.forever.constant.LanguageConstant;

/**
 * Created by DELL on 2017/9/25.
 */
@Component
public class LanguageUtil {

	private static final Logger logger = LoggerFactory.getLogger(LanguageUtil.class);

	private static MessageSource messageSource;

	@Autowired
	public void setRedisTemplate(MessageSource messageSource) {
		LanguageUtil.messageSource = messageSource;
	}

	public static String getMsg(LanguageConstant languageConstant) {
		String value;
		try {
			Locale locale = LocaleContextHolder.getLocale();
			value = messageSource.getMessage(languageConstant.getCode(), null, locale);
		} catch (NoSuchMessageException ns) {
			value = languageConstant.getMsgZh();
			logger.error(ns.getMessage());
		}
		return value;
	}

	public static String getMsg(LanguageConstant languageConstant, Object... params) {
		String value;
		try {
			Locale locale = LocaleContextHolder.getLocale();
			value = messageSource.getMessage(languageConstant.getCode(), null, locale);
		} catch (NoSuchMessageException ns) {
			value = languageConstant.getMsgZh();
			logger.error(ns.getMessage());
		}
		return String.format(value, params);
	}
}
