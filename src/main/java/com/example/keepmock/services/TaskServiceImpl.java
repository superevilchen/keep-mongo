package com.example.keepmock.services;

import com.example.keepmock.beans.Label;
import com.example.keepmock.beans.Task;
import com.example.keepmock.exceptions.CustomException;
import com.example.keepmock.repos.MongoTemplateImpl;
import com.example.keepmock.repos.TaskRepository;
import com.example.keepmock.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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

//    @Override
//    public boolean login(String email, String password) throws CustomException {
//
//        userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new CustomException(LOGIN_FAILED));
//        return true;
//    }

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

        if (!taskRepository.existsById(taskID)){
            throw new CustomException(NOT_FOUND);
        }

        taskRepository.deleteById(taskID);
    }

    // discarded/archived
    public void changeTaskStatus(String taskID, String userID, String field, boolean fieldStatus){
        mongoTemplateImpl.changeTaskStatus(taskID, userID, field, fieldStatus);
    }

//    // TODO - these can be together and what about de-discarding and de-archiving?
//    @Override
//    public void discardTask(String taskID, String userID) {
//        mongoTemplateImpl.changeTaskStatus(taskID, userID, "isDiscarded", true);
//    }
//
//    @Override
//    public void archiveTask(String taskID, String userID) {
//        mongoTemplateImpl.changeTaskStatus(taskID, userID, "isArchived", false);
//    }

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

    // if true, asc
    // if false, desc
    public List<Task> sort(String userID, boolean sortingDirection, String field){

        if (sortingDirection){
            return mongoTemplateImpl.sort(userID, Sort.Direction.ASC, field);
        }

        return mongoTemplateImpl.sort(userID, Sort.Direction.DESC, field);
    }
//
//    @Override
//    public List<Task> sortFromNewToOld(String userID) {
//        return mongoTemplateImpl.sort(userID, Sort.Direction.ASC, "addedAt");
//    }
//
//    @Override
//    public List<Task> sortFromOldToNew(String userID) {
//        return mongoTemplateImpl.sort(userID, Sort.Direction.DESC, "addedAt");
//    }
//
//    @Override
//    public List<Task> sortFromFarthest(String userID) {
//        return mongoTemplateImpl.sort(userID, Sort.Direction.DESC, "dueDate");
//    }
//
//    @Override
//    public List<Task> sortFromNearest(String userID) {
//        return mongoTemplateImpl.sort(userID, Sort.Direction.ASC, "dueDate");
//    }

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

    // discarded/archived
    public List<Task> getAllPerFieldStatus(String userID, boolean fieldStatus, String field){
        return mongoTemplateImpl.getAllPerField(userID, fieldStatus, field);
    }

//    @Override
//    // fix
//    public List<Task> getAllArchived(String userID) {
//        return mongoTemplateImpl.getAllPerField(userID, true, "isArchived");
//    }
//
//    @Override
//    //fix
//    public List<Task> getAllDiscarded(String userID) {
//        return mongoTemplateImpl.getAllPerField(userID, true, "isDiscarded ");
//    }

    @Override
    public List<Task> getAllUnderLabel(Label label, String userID) {
        return taskRepository.findByLabelAndUserId(label, userID);
    }
}
