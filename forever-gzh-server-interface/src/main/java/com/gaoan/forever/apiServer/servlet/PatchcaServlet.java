package com.gaoan.forever.apiServer.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.utils.cache.CacheUtils;

/**
 * @author longshengtang
 * @since 20170409
 */
@WebServlet(name = "Captcha", urlPatterns = "/api/static_code")
public class PatchcaServlet extends HttpServlet {

    public PatchcaServlet() {

    }

    private static final long serialVersionUID = -656795564528376490L;

    private static final Logger LOGGER = LoggerFactory.getLogger(PatchcaServlet.class);

    private static int WIDTH = 110;
    private static int HEIGHT = 50;
    private static int MAX_LENGTH = 4;
    private static int MIN_LENGTH = 4;

    private static ConfigurableCaptchaService cs;

    private static volatile boolean inited = false;

    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
    	LOGGER.info("");
        cs = new ConfigurableCaptchaService();
        int width = NumberUtils.toInt(this.getInitParameter("width"), WIDTH);
        int height = NumberUtils.toInt(this.getInitParameter("height"), HEIGHT);
        cs.setWidth(width);
        cs.setHeight(height);

        int maxLength = NumberUtils.toInt(this.getInitParameter("maxLength"), MAX_LENGTH);
        int minLength = NumberUtils.toInt(this.getInitParameter("minLength"), MIN_LENGTH);
        RandomWordFactory wf = new RandomWordFactory();
        wf.setMaxLength(maxLength);
        wf.setMinLength(minLength);

        cs.setWordFactory(wf);
    }


    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //必须移动到此，否则会报错
        String sessionId = request.getSession().getId();
        // 清除缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);

        // 显示类型
        response.setContentType("image/png");

        OutputStream os = response.getOutputStream();
        String patchca = EncoderHelper.getChallangeAndWriteImage(cs, "png", os);
//        Element element = new Element(sessionId, patchca);

        // 放入缓存
//        CacheUtils.put(sessionId, patchca);
//        request.getSession().setAttribute(sessionId, patchca);
        CacheUtils.put(sessionId, patchca);

        os.flush();
        os.close();
    }

    /**
     * 检查验证码是否正确
     *
     * @return
     */
    public static boolean validate(String sessionId, String code) {
        return CacheUtils.validateAndRemove(sessionId, code);
    }

    @Override
    public void destroy() {
        cs = null;
    }
}