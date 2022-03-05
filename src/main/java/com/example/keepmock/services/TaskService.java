package com.example.keepmock.services;

import com.example.keepmock.beans.Label;
import com.example.keepmock.beans.Task;
import com.example.keepmock.exceptions.CustomException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TaskService {

    boolean login(String email, String password) throws CustomException;
    void addTask(Task task) throws CustomException;
    void updateTask(String taskID, Task task) throws CustomException;
    void deleteTask(String taskID) throws CustomException;
    void changeTaskStatus(String taskID, String field) throws CustomException;
    List<Task> getAllUserTasks(String userID) throws CustomException;
    Task getOneUserTask(String taskID) throws CustomException;
    List<Task> sort(String userID, boolean isAsc, String field) throws CustomException;
    List<Task> getAllBefore(LocalDateTime date, String userID) throws CustomException;
    List<Task> getAllAfter(LocalDateTime date, String userID) throws CustomException;
    List<Task> getAllBetween(LocalDateTime start, LocalDateTime end, String userID) throws CustomException;
    List<Task> getAllPerFieldStatus(String userID, boolean fieldStatus, String field) throws CustomException;
    List<Task> getAllUnderLabel(Label label, String userID) throws CustomException;
}
