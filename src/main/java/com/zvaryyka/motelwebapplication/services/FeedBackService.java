package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.FeedBackDTO;
import com.zvaryyka.motelwebapplication.models.FeedBack;
import com.zvaryyka.motelwebapplication.repositories.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackService {
    private final FeedBackRepository feedBackRepository;

    public List<FeedBack> getAllFeedBacks() {
        return feedBackRepository.getAllFeedBacks();
    }
    public List<FeedBackDTO> getAllFeedBacksDTO() {
        return feedBackRepository.getAllFeedBacksDTO();
    }

    @Autowired
    public FeedBackService(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    public void save(FeedBack feedBack) {
        feedBackRepository.save(feedBack);
    }
}
