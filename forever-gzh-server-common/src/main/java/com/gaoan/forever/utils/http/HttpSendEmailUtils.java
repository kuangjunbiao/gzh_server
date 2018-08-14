package com.gaoan.forever.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * 用于发送邮件工具
 *
 * @author longshengtang
 * @date 20170509
 */
public class HttpSendEmailUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpSendEmailUtils.class);

    public static boolean send(String appid,String secretkey,String url ,String subject, String content, String email) {
        CloseableHttpResponse response = null;
        InputStream is = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();
            //封装请求参数
            List<NameValuePair> params = Lists.newArrayList();
            params.add(new BasicNameValuePair("appid", appid));
            params.add(new BasicNameValuePair("secretkey", secretkey));
            params.add(new BasicNameValuePair("t", subject));
            params.add(new BasicNameValuePair("c", content));
            params.add(new BasicNameValuePair("e", email));
            String str = "";
            //转换为键值对
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            logger.info("邮件参数："+str);
            //创建Get请求
            HttpGet httpGet = new HttpGet(url + "?" + str);
            logger.info("开始发送邮件：");
            Long s = System.nanoTime();
            //执行Get请求，
            response = httpClient.execute(httpGet);
            Long e = System.nanoTime();
            logger.info("调用邮箱接口发送邮件完成耗时(纳秒)：" + String.format("%,d", (e - s)));
            //得到响应体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                is = entity.getContent();
                //转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                String body = null;
//                StringBuilder sb = new StringBuilder();
                String result = null;
                while ((body = br.readLine()) != null) {
                    result = body;
                    logger.debug("body="+body);
                }
                if ("1".equals(result)) {
                    return true;
                }
                return false;
            }
        } catch (ParseException e) {
            logger.info("发送邮件异常：" + e.getMessage());
        } catch (Exception e) {
            logger.info("发送邮件异常：" + e.getMessage());
        } finally {
            close(response, is, httpClient);
        }
        return false;
    }


    /**
     * 关闭相应流
     *
     * @param response
     * @param is
     * @param httpClient
     */
    private static void close(CloseableHttpResponse response, InputStream is, CloseableHttpClient httpClient) {
        //关闭输入流，释放资源
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                logger.error("发送邮件关闭is异常：" + e.getMessage());
            }
        }
        //消耗实体内容
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                logger.error("发送邮件关闭response异常：" + e.getMessage());
            }
        }
        //关闭相应 丢弃http连接
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("发送邮件关闭httpClient异常：" + e.getMessage());
            }
        }
    }

//}

    public static void main(String[] args) {
//        SendMailReq req = new SendMailReq();
//        req.setMail("1845797136@qq.com");
//        req.setMailCode("123456");
//        req.setSubject("注册");
//        BaseResp result = HttpUtils.send(req);
//        System.out.println("result===============================" + result);
//        System.out.println(String.format("%,d", 1234567));
//        String url = "http://api.qeveworld.biz/api/mail/thirdparysendmail";
//        String appid = "0be987167bba4b80b2b68889378e5140";
//        String secretkey = "c035c839e00e706b1bb9c0209e9ee8f6";
//        HttpSendEmailUtils.send(appid, secretkey, url, "哈哈哈", "llllasdfasdf驾驶的范围", "mail@qeveworld.com");
    }
}