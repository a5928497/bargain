package com.yukoon.bargain.services;

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

    @Transactional
    public void saveAct(Activity activity) {
        activityRepo.saveAndFlush(activity);
    }

    @Transactional
    public Activity findById(Integer id) {
        return activityRepo.findOne(id);
    }

}
