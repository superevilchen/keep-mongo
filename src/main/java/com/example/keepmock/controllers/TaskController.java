package com.example.keepmock.controllers;

import com.example.keepmock.beans.Label;
import com.example.keepmock.beans.Task;
import com.example.keepmock.exceptions.CustomException;
import com.example.keepmock.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks/")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestBody Task task) throws CustomException{
        taskService.addTask(task);
    }

    @PutMapping("taskID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTask(@PathVariable String taskID, @RequestBody Task task) throws CustomException{
        taskService.updateTask(taskID, task);
    }

    @DeleteMapping("{taskID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String taskID) throws CustomException{
        taskService.deleteTask(taskID);
    }

    @PutMapping("status/{taskID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeTaskStatus(@PathVariable String taskID, @RequestParam String field) throws CustomException{
        taskService.changeTaskStatus(taskID, field);
    }

    @GetMapping("userID")
    public List<Task> getAllUserTasks(@PathVariable String userID) throws CustomException{
        return taskService.getAllUserTasks(userID);
    }

    @GetMapping("task/{taskID}")
    public Task getOneUserTask(@PathVariable String taskID) throws CustomException{
        return taskService.getOneUserTask(taskID);
    }

    @GetMapping("sort")
    public List<Task> sort(@RequestParam String userID, @RequestParam boolean isAsc, @RequestParam String field) throws CustomException{
        return taskService.sort(userID, isAsc, field);
    }

    @GetMapping("before")
    public List<Task> getAllBefore(@RequestParam LocalDateTime date, @RequestParam String userID) throws CustomException{
        return taskService.getAllBefore(date, userID);
    }

    @GetMapping("after")
    public List<Task> getAllAfter(@RequestParam LocalDateTime date, @RequestParam String userID) throws CustomException{
        return taskService.getAllAfter(date, userID);
    }

    @GetMapping("between")
    public List<Task> getAllBetween(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end, @RequestParam String userID) throws CustomException{
        return taskService.getAllBetween(start, end, userID);
    }

    @GetMapping("field")
    public List<Task> getAllPerFieldStatus(@RequestParam String userID, @RequestParam boolean fieldStatus, @RequestParam String field) throws CustomException{
        return taskService.getAllPerFieldStatus(userID, fieldStatus, field);
    }

    @GetMapping("label")
    public List<Task> getAllUnderLabel(@RequestParam Label label, @RequestParam String userID) throws CustomException{
        return taskService.getAllUnderLabel(label, userID);
    }
}
