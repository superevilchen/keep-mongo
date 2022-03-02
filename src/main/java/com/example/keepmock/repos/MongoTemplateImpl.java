package com.example.keepmock.repos;

import com.example.keepmock.beans.Task;
import com.example.keepmock.exceptions.CustomException;
import com.example.keepmock.exceptions.ExceptionState;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.keepmock.exceptions.ExceptionState.INVALID_FIELD;

@Repository
@RequiredArgsConstructor
public class MongoTemplateImpl {

    private final MongoTemplate mongoTemplate;

    @Transactional
    public void changeTaskStatus(String taskID, String field){

        Task fromDB = mongoTemplate.findById(taskID, Task.class);

        switch (field){
            case "isDiscarded":
                fromDB.setDiscarded(!fromDB.isDiscarded());
                break;
            case "isArchived":
                fromDB.setArchived(!fromDB.isArchived());
                break;
        }
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

    public boolean getTaskStatus(String taskID, String field) throws CustomException {

        Task task = mongoTemplate.findById(taskID, Task.class);

        switch (field){
            case "isDiscarded":
                return task.isDiscarded();
            case "isArchived":
                return task.isArchived();
        }

        throw new CustomException(INVALID_FIELD);
    }
}
