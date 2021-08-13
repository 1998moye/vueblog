package com.myblog.controller;

import com.myblog.res.Result;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class MyFileController {
    @PostMapping("/upload/{type}")
    public Result upload(@PathVariable("type") String type, MultipartFile file){
        System.out.println("进入文件上传");
        String fileWebpath = "";
        String fileName = "";
        if(file != null){
            if(!file.isEmpty()){
                String originalFilename = file.getOriginalFilename();
                System.out.println(originalFilename);
                String substring = originalFilename.substring(originalFilename.indexOf("."));
                fileName = UUID.randomUUID() + substring;
                String filepathPre = "";
                if(System.getProperty("os.name").toLowerCase().contains("windows")){
                    filepathPre = "d:/upload";
                }else{
                    filepathPre = "/upload";
                }

                if(type != null){
                    fileWebpath = "/img/" + type + "/";
                }else{
                    fileWebpath = "/img/others/";
                }

                String fileRealPath = filepathPre + fileWebpath + fileName;
                File file1 = new File(fileRealPath);
                if(!file1.getParentFile().exists()){
                    file1.mkdirs();
                }
                try {
                    file.transferTo(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Result.success(null, fileWebpath + fileName);
        }
        return Result.fail("上传失败", null);
    }

    @GetMapping("/removeImg")
    public Result removeFile(@RequestParam("img") String imgPath){
        String locationPre = "";
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            locationPre = "d:/upload";
        }else{
            locationPre = "/upload";
        }
        String filepath = locationPre + imgPath;
        File file = new File(filepath);
        if(file.exists()){
            file.delete();
        }
        return Result.success(null, null);
    }
}
