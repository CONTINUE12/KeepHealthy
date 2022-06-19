package com.wlsup.yygh.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    //上传文件到阿里云oss
    String upload(MultipartFile file);
}
