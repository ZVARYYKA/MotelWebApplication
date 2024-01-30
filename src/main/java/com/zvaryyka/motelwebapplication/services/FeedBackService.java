package com.zvaryyka.motelwebapplication.services;

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

    @Autowired
    public FeedBackService(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }
}
