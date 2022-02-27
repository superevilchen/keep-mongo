package com.example.keepmock.repos;

import com.example.keepmock.beans.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MongoTemplateImpl {

    private final MongoTemplate mongoTemplate;

    public void discardTask(String taskID, String userID){

        //TODO - possible to write shorter code here

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(taskID).and("user.id").is(userID));
        Update update = new Update();
        update.set("isDiscarded", true);

        mongoTemplate.findAndModify(query, update, Task.class);
    }

    public void archiveTask(String taskID, String userID){

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(taskID).and("user.id").is(userID));
        Update update = new Update();
        update.set("isArchived", true);

        mongoTemplate.findAndModify(query, update, Task.class);
    }

    public List<Task> sortFromNearest(String userID){

        //TODO - and here shorter

        Query query = new Query();
        query.addCriteria(Criteria.where("user.id").is(userID));
        query.with(Sort.by(Sort.Direction.ASC, "dueDate"));
        return mongoTemplate.find(query, Task.class);
    }

    public List<Task> sortFromFarthest(String userID){

        Query query = new Query();
        query.addCriteria(Criteria.where("user.id").is(userID));
        query.with(Sort.by(Sort.Direction.DESC, "dueDate"));
        return mongoTemplate.find(query, Task.class);
    }

    public List<Task> sortFromOldToNew(String userID){

        Query query = new Query();
        query.addCriteria(Criteria.where("user.id").is(userID));
        query.with(Sort.by(Sort.Direction.DESC, "addedAt"));
        return mongoTemplate.find(query, Task.class);
    }

    public List<Task> sortFromNewToOld(String userID){

        Query query = new Query();
        query.addCriteria(Criteria.where("user.id").is(userID));
        query.with(Sort.by(Sort.Direction.ASC, "addedAt"));
        return mongoTemplate.find(query, Task.class);
    }

    public void archiveExpired(){

        Query query = new Query();
        query.addCriteria(Criteria.where("dueDate").lt(LocalDateTime.now()));
        Update update = new Update();
        update.set("isArchived", true);

        mongoTemplate.findAndModify(query, update, Task.class);
    }

    public void DeleteOldDiscardedJob(){

        Query query = new Query();
        query.addCriteria(Criteria.where("updatedAt").lt(LocalDateTime.now().minusMonths(2)).and("isDiscarded").is(true));

        mongoTemplate.findAllAndRemove(query, Task.class);
    }
}
