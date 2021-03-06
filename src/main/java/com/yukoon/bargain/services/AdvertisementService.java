package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.Advertisement;
import com.yukoon.bargain.repository.AdvertisementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepo advertisementRepo;
    private final static String ADV_BACKGROUND_PATH = "";

    @Transactional
    public Advertisement addAdv(Advertisement advertisement) {
        Advertisement result = advertisementRepo.saveAndFlush(advertisement);
        return result;
    }

    @Transactional
    public List<Advertisement> findAllByActId(Integer act_id) {
        return advertisementRepo.findAllByActId(act_id);
    }

    @Transactional
    public Advertisement findById(Integer adv_id) {
        return advertisementRepo.findById(adv_id);
    }

    @Transactional
    public void deleteById(Integer id) {
        advertisementRepo.delete(id);
    }
}
