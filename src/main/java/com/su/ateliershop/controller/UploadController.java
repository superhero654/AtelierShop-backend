package com.su.ateliershop.controller;

import com.su.ateliershop.common.AuthContext;
import com.su.ateliershop.service.OssService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class UploadController {

    private final OssService ossService;
    private final AuthContext authContext;

    public UploadController(OssService ossService, AuthContext authContext) {
        this.ossService = ossService;
        this.authContext = authContext;
    }

    @PostMapping("/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        authContext.requireAdmin(request);
        String url = ossService.upload(file);
        return Map.of("url", url);
    }
}
