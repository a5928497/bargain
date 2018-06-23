package com.yukoon.bargain.entities.services;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.repository.ActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepo activityRepo;

    @Transactional
    public List<Activity> findAll() {
        return activityRepo.findAll();
    }
}
