package com.example.keepmock.repos;

import com.example.keepmock.beans.Label;
import com.example.keepmock.beans.Task;
import com.example.keepmock.beans.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findByUserId(String UserID);

    Optional<Task> findByIdAndUserId(String taskID, String userID);

    List<Task> findByDueDateGreaterThanAndUserId(LocalDateTime date, String userID);

    List<Task> findByDueDateLessThanAndUserId(LocalDateTime date, String userID);

    List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end);

    List<Task> findByIsArchivedIsTrueAndUserId(String userID);

    List<Task> findByIsDiscardedIsTrueAndUserId(String userID);

    List<Task> findByLabelAndUserId(Label label, String userID);

}
