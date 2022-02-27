package com.example.keepmock.jobs;

import com.example.keepmock.repos.MongoTemplateImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteOldDiscardedJob {

    private final MongoTemplateImpl mongoTemplate;

    @Scheduled(initialDelayString = "PT5S", fixedDelayString = "PT1H")
    public void run(){

        mongoTemplate.DeleteOldDiscardedJob();

    }
}
