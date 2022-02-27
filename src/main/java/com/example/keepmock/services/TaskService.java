package com.example.keepmock.services;

import com.example.keepmock.beans.Label;
import com.example.keepmock.beans.Task;
import com.example.keepmock.exceptions.CustomException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TaskService {

//    boolean login(String email, String password) throws CustomException;
    void addTask(Task task) throws CustomException;
    void updateTask(String taskID, Task task, String userID) throws CustomException;
    void deleteTask(String taskID) throws CustomException;
    void discardTask(String taskID, String userID) throws CustomException;
    void archiveTask(String taskID, String userID);
    List<Task> getAllUserTasks(String userID) throws CustomException;
    Task getOneUserTask(String taskID, String userID) throws CustomException;
    List<Task> sortFromNewToOld(String userID);
    List<Task> sortFromOldToNew(String userID);
    List<Task> sortFromFarthest(String userID);
    List<Task> sortFromNearest(String userID);
    List<Task> getAllBefore(LocalDateTime date, String userID);
    List<Task> getAllAfter(LocalDateTime date, String userID);
    List<Task> getAllBetween(LocalDateTime start, LocalDateTime end, String userID);
    List<Task> getAllArchived(String userID);
    List<Task> getAllDiscarded(String userID);
    List<Task> getAllUnderLabel(Label label, String userID);
}
