package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.FeedBackDTO;
import com.zvaryyka.motelwebapplication.models.FeedBack;
import com.zvaryyka.motelwebapplication.repositories.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class FeedBackService {
    private final FeedBackRepository feedBackRepository;

    @Autowired
    public FeedBackService(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    public List<FeedBack> getAllFeedBacks() {
        return feedBackRepository.getAllFeedBacks();
    }

    public List<FeedBackDTO> getAllFeedBacksDTO() {
        return feedBackRepository.getAllFeedBacksDTO();
    }

    public void save(FeedBack feedBack) {
        log.info("Saving feedback: {}", feedBack);
        feedBackRepository.save(feedBack);
        log.info("Feedback saved successfully");
    }
}
