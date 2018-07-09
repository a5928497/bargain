package com.yukoon.bargain.controllers;

import com.yukoon.bargain.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UploadController {
    @Autowired
    private UploadService uploadService;
}
