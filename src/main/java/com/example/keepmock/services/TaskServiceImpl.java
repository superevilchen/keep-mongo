package com.example.keepmock.services;

import com.example.keepmock.beans.Label;
import com.example.keepmock.beans.Task;
import com.example.keepmock.beans.User;
import com.example.keepmock.exceptions.CustomException;
import com.example.keepmock.repos.MongoTemplateImpl;
import com.example.keepmock.repos.TaskRepository;
import com.example.keepmock.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.keepmock.exceptions.ExceptionState.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MongoTemplateImpl mongoTemplateImpl;

    @Override
    public boolean login(String email, String password) throws CustomException {

        userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new CustomException(LOGIN_FAILED));
        return true;
    }

    // TODO -validations
    @Override
    public void addTask(Task task) throws CustomException {

        // if task is already found throw error


        // validations
        taskRepository.save(task);
    }

    @Override
    public void updateTask(String taskID, Task task, String userID) throws CustomException {
        // TODO - think if userid is needed
        // fix
        userRepository.findById(userID).orElseThrow(() -> new CustomException(NOT_FOUND));
        taskRepository.findById(taskID).orElseThrow(() -> new CustomException(NOT_FOUND));

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(String taskID) throws CustomException {

        // maybe validate if this task belongs to this user?

        if (!taskRepository.existsById(taskID)){
            throw new CustomException(NOT_FOUND);
        }

        taskRepository.deleteById(taskID);
    }

    @Override
    public void discardTask(String taskID, String userID) {
        mongoTemplateImpl.discardTask(taskID, userID);
    }

    @Override
    public void archiveTask(String taskID, String userID) {
        mongoTemplateImpl.archiveTask(taskID, userID);
    }

    @Override
    // fix
    public List<Task> getAllUserTasks(String userID){
        return taskRepository.findByUserId(userID);
    }

    @Override
    //fix
    public Task getOneUserTask(String taskID, String userID) throws CustomException {
        return taskRepository.findByIdAndUserId(taskID, userID).orElseThrow(() -> new CustomException(NOT_FOUND));
    }

    @Override
    public List<Task> sortFromNewToOld(String userID) {
        return mongoTemplateImpl.sortFromNewToOld(userID);
    }

    @Override
    public List<Task> sortFromOldToNew(String userID) {
        return mongoTemplateImpl.sortFromOldToNew(userID);
    }

    @Override
    public List<Task> sortFromFarthest(String userID) {
        return mongoTemplateImpl.sortFromFarthest(userID);
    }

    @Override
    public List<Task> sortFromNearest(String userID) {
        return mongoTemplateImpl.sortFromNearest(userID);
    }

    @Override
    public List<Task> getAllBefore(LocalDateTime date, String userID) {
        return taskRepository.findByDueDateLessThanAndUserId(date, userID);
    }

    @Override
    public List<Task> getAllAfter(LocalDateTime date, String userID) {
        return taskRepository.findByDueDateGreaterThanAndUserId(date, userID);
    }

    @Override
    public List<Task> getAllBetween(LocalDateTime start, LocalDateTime end, String userID) {
        return taskRepository.findByDueDateBetween(start, end);
    }

    @Override
    // fix
    public List<Task> getAllArchived(String userID) {
        return taskRepository.findByIsArchivedIsTrueAndUserId(userID);
    }

    @Override
    //fix
    public List<Task> getAllDiscarded(String userID) {
        return taskRepository.findByIsDiscardedIsTrueAndUserId(userID);
    }

    @Override
    public List<Task> getAllUnderLabel(Label label, String userID) {
        return taskRepository.findByLabelAndUserId(label, userID);
    }
}
