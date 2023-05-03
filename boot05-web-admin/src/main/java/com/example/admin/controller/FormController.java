package com.example.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
public class FormController {

    @GetMapping("/form_layouts")
    public String form_layouts(){
        return "form/form_layouts";
    }

    /**
     * MultipartFile 自动封装上传过来的文件
     * @param email
     * @param username
     * @param headerImg
     * @param photos
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam String email,
                         @RequestParam String username,
                         @RequestPart MultipartFile headerImg,
                         @RequestPart MultipartFile[] photos) throws IOException {
        log.info("上传的信息：email-->{},username-->{},headerImg-->{},photos-->{}",
                email,username,headerImg.getSize(),photos.length);
        if(!headerImg.isEmpty()){
            //保存到文件服务器，OSS服务器,此处保存在本地
            String originalFilename = headerImg.getOriginalFilename();
            headerImg.transferTo(new File("E:\\IdeaProject\\springboot\\boot05-web-admin\\img\\" + originalFilename));
        }
        if (photos.length>0){
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()){
                    String originalFilename = photo.getOriginalFilename();
                    photo.transferTo(new File("E:\\IdeaProject\\springboot\\boot05-web-admin\\img\\"  + originalFilename));
                }
            }
        }
        return "main";
    }
}
