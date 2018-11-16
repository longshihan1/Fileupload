package com.longshihan.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/file")
public class FileController {

    /**
     * 本地访问内容地址 ：http://localhost:8080/file
     * @param map
     * @return
     */
    @RequestMapping("/")
    public String helloHtml(HashMap<String, Object> map) {
        map.put("hello", "欢迎进入HTML页面");
        return "upload";
    }

    /**
     * 文件上传具体实现方法;
     *
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        String filename;
        String path="--";
        if (!file.isEmpty()) {
            try {
                filename = getRandomFileName() + "_" + file.getOriginalFilename();
                File file1 = new File( "/data/"+filename);
                if (!file1.exists())
                    file1.createNewFile();
                path=file1.getCanonicalPath();
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(file1));
                System.out.println(file.getName());
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                return APIResponce.fail("上传失败 FileNotFoundException," + e.getMessage()+":::::"+path).toString();
            } catch (IOException e) {
                return APIResponce.fail("上传失败 IOException," + e.getMessage()+":::::"+path).toString();
            } catch (Exception e){
                return APIResponce.fail("上传失败 Exception," + e.getMessage()+":::::"+path).toString();
            }
            return new APIResponce(new FileEntity(filename+":"+path)).toString();
        } else {
            return APIResponce.fail("上传失败，因为文件是空的.").toString();
        }
    }





    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String name=params.getParameter("name");
        System.out.println("name:"+name);
        String id=params.getParameter("id");
        System.out.println("id:"+id);
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File("/var/images/"+file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        return "upload successful";
    }


    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return str+"_"+rannum ;// 当前时间
    }

}
