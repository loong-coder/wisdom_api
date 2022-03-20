package com.edu.controller;

import com.edu.domain.response.BaseResponse;
import com.edu.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/file")
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }



    @PostMapping
    @ResponseBody
    public BaseResponse<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        String url = storageService.store(file);
        BaseResponse<String> result = new BaseResponse<>();
        result.setData(url);
        return result;
    }
}