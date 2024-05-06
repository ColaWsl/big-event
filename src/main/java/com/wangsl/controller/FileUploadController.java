package com.wangsl.controller;

import com.wangsl.pojo.Result;
import com.wangsl.utils.Md5Util;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

	@PostMapping
	public Result<String> upload(MultipartFile file) throws IOException {
		String originalFilename = file.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		String filename = UUID.randomUUID() + suffix;
		file.transferTo(new File("D:\\java\\BigEvent\\resources\\02_随堂资料\\04_综合案例资料\\02_后台资料\\files\\" + filename));
		return Result.success(filename);
	}
}
