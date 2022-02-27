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

    public void changeTaskStatus(String taskID, String userID, String field, boolean fieldStatus){

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(taskID).and("user.id").is(userID));
        Update update = new Update();
        update.set(field, fieldStatus);

        mongoTemplate.findAndModify(query, update, Task.class);
    }

    public List<Task> getAllPerField(String userID, boolean fieldStatus ,String field){
        Query query = new Query();
        query.addCriteria(Criteria.where(field).is(fieldStatus).and("user.id").is(userID));
        return mongoTemplate.find(query, Task.class);
    }

    public List<Task> sort(String userID, Sort.Direction way, String field){
        Query query = new Query();
        query.addCriteria(Criteria.where("user.id").is(userID));
        query.with(Sort.by(way, field));
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
