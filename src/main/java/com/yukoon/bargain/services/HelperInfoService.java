package com.yukoon.bargain.services;

import com.yukoon.bargain.repository.HelperInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelperInfoService {
    @Autowired
    private HelperInfoRepo helperInfoRepo;
}
