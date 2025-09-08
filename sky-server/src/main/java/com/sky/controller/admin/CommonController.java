package com.sky.controller.admin;

import com.sky.config.OssConfiguration;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequestMapping("/admin/common")
@RestController
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    // 使用阿里云OSS做文件上传
    @PostMapping("/upload")
    public Result uploead(MultipartFile file) throws IOException {
        log.info("文件上传：{}", file.getOriginalFilename());

        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        // 提取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 生成唯一文件名
        String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + suffix;

        String url = aliOssUtil.upload(file.getBytes(), uniqueFileName);
        return Result.success(url);
    }
}
