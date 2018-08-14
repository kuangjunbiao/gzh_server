package com.gaoan.forever.utils.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Iterator;

/**
 * Created by Tianho on 2016/7/27.
 */
public class FileUtils {

    /**
     * 创建文件目录
     * @param destPath
     * @return
     */
    public static boolean mkdirs(String destPath) {
        File file = new File(destPath);
        if(!file.getParentFile().exists()){
            return  file.getParentFile().mkdirs();
        }
        return true;
    }
    /**
     * 递归删除文件夹
     */
    public static boolean deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        }
        return true;
    }

   /* public static void main(String args[]){

    }*/

    public static String readWantedText(HttpServletRequest request){
        StringBuffer sbf = new StringBuffer();
        try {
            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();

                while (iter.hasNext()) {
                    // 记录上传过程起始时的时间，用来计算上传时间
                    int pre = (int) System.currentTimeMillis();
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        InputStream  inputStream = file.getInputStream();
                        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8"); // 最后的"GBK"根据文件属性而定，如果不行，改成"UTF-8"
                        BufferedReader br = new BufferedReader(reader);
                        String line;
                        while ((line = br.readLine()) != null) {
                            sbf.append(line);
                        }
                        br.close();
                        reader.close();
                }
            }
        }
    }catch (Exception e){
        throw e;
    }finally {
        return sbf.toString();
    }
}
    public static String readWantedText(String filePath) {
        StringBuffer sbf = new StringBuffer();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String temp = "";// 用于临时保存每次读取的内容
            while (temp != null) {
                temp = br.readLine();
                if (temp != null ) {
                    sbf.append(temp);
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            return sbf.toString();
        }
    }
}
