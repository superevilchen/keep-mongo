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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.keepmock.exceptions.ExceptionState.*;
import static com.example.keepmock.utils.ServiceUtils.*;

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

    @Override
    public void addTask(Task task) throws CustomException {

        //TODO - validation fails because id is null

        if (task.getId() != null){
            validate(!taskRepository.existsById(task.getId()), () -> new CustomException(ALREADY_EXISTS));
        }

        taskRepository.save(task);
    }

    @Override
    @Transactional(value = "MONGO_TRANSACTION_MANAGER", propagation = Propagation.REQUIRED)
    public void updateTask(String taskID, Task task) throws CustomException {

        Task fromDB = taskRepository.findById(taskID).orElseThrow(() -> new CustomException(NOT_FOUND));

        fromDB.setText(task.getText());
        fromDB.setLabel(task.getLabel());
        fromDB.setPicture(task.getPicture());

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(String taskID) throws CustomException {

        validate(taskRepository.existsById(taskID), () -> new CustomException(NOT_FOUND));

        taskRepository.deleteById(taskID);
    }

    // discarded/archived
    public void changeTaskStatus(String taskID, String field) throws CustomException {

        validate(taskRepository.existsById(taskID), () -> new CustomException(NOT_FOUND));
        validateField(field);

        mongoTemplateImpl.changeTaskStatus(taskID, field);
    }

    @Override
    // fix
    public List<Task> getAllUserTasks(String userID) throws CustomException {

        validate(userRepository.existsById(userID), () -> new CustomException(NOT_FOUND));

        return taskRepository.findByUserId(userID);
    }

    @Override
    //fix
    public Task getOneUserTask(String taskID) throws CustomException {

        return taskRepository.findById(taskID).orElseThrow(() -> new CustomException(NOT_FOUND));
    }

    // if true, asc
    // if false, desc
    public List<Task> sort(String userID, boolean isAsc, String field) throws CustomException {

        // validate fields

        validate(userRepository.existsById(userID), () -> new CustomException(NOT_FOUND));

        validateMany(
                List.of(field.equals("text") ||
                        field.equals("addedAt") ||
                        field.equals("updatedAt") ||
                        field.equals("dueDate") ||
                        field.equals("label")),
                () -> new CustomException(INVALID_FIELD));

        if (isAsc){
            return mongoTemplateImpl.sort(userID, Sort.Direction.ASC, field);
        }

        return mongoTemplateImpl.sort(userID, Sort.Direction.DESC, field);
    }

    @Override
    public List<Task> getAllBefore(LocalDateTime date, String userID) throws CustomException {

        validate(userRepository.existsById(userID), () -> new CustomException(NOT_FOUND));

        return taskRepository.findByDueDateLessThanAndUserId(date, userID);
    }

    @Override
    public List<Task> getAllAfter(LocalDateTime date, String userID) throws CustomException {

        validate(userRepository.existsById(userID), () -> new CustomException(NOT_FOUND));

        return taskRepository.findByDueDateGreaterThanAndUserId(date, userID);
    }

    @Override
    public List<Task> getAllBetween(LocalDateTime start, LocalDateTime end, String userID) throws CustomException {

        validate(userRepository.existsById(userID), () -> new CustomException(NOT_FOUND));
        validate(end.isAfter(start), () -> new CustomException(INVALID_DATE));

        // validate date
        return taskRepository.findByDueDateBetween(start, end);
    }

    // discarded/archived
    public List<Task> getAllPerFieldStatus(String userID, boolean fieldStatus, String field) throws CustomException {

        validate(userRepository.existsById(userID), () -> new CustomException(NOT_FOUND));
        validateField(field);

        return mongoTemplateImpl.getAllPerField(userID, fieldStatus, field);
    }

    @Override
    public List<Task> getAllUnderLabel(Label label, String userID) throws CustomException {

        validateMany
                (List.of(userRepository.existsById(userID),
                         label.getLabelName() != null,
                         taskRepository.findByLabel(label).isPresent()),
                         () -> new CustomException(NOT_FOUND));

        return taskRepository.findByLabelAndUserId(label, userID);
    }
}
