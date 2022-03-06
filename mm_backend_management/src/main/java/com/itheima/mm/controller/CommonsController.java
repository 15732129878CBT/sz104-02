package com.itheima.mm.controller;


import com.itheima.mm.entity.Result;
import com.itheima.mm.utils.JsonUtils;
import com.itheima.mm.utils.UploadUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author cbt
 * @createDate 2021-11-13
 */
@RestController
@RequestMapping("/common")
public class CommonsController {

    @RequestMapping("/uploadFile")
    public Result uploadFils(MultipartFile upload, HttpSession session) throws IOException {
        InputStream is = null;
        FileOutputStream os = null;
        String imgUrl = null;
        try {
            //获取文件名
            String fileName = upload.getOriginalFilename();
            //上传图片名可能发生重名,怎么解决这个问题呢?使用UUID来生成唯一的文件名
            fileName = UploadUtils.getUUIDName(fileName);

            //一个目录中不能存放过多文件：可以选择创建多级目录
            String randomDir = UploadUtils.getDir();



            //获取上传的文件
            is = upload.getInputStream();

            //2. 准备一个目录存储客户端上传的图片
            //获取webapp里面的img里面的upload目录的路径(部署的项目中的upload文件夹的路径)
            String uploadPath = session.getServletContext().getRealPath("img/upload"+randomDir);
            File file = new File(uploadPath);
            if (!file.exists()) {
                //判断系统中是否存在该目录，如果不存在则创建
                file.mkdirs();
            }

            //3. 使用输出流将客户端上传的图片，存储到准备的目录中
            os = new FileOutputStream(new File(file,fileName));
            //使用IOUtils将is中的数据拷贝到os
            IOUtils.copy(is,os);

            //客户端需要的图片路径
            imgUrl = "/img/upload"+randomDir+"/"+fileName;
            //因为客户端要进行图片回显，那么服务器端得将图片路径响应给客户端
            //图片上传成功
           return new Result(true,"图片上传成功",imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,"图片上传失败");
        }finally {
            os.close();
            is.close();
        }
    }
}
