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

    // TODO - add login and update and check about request params...

//    private boolean login(String email, String password){
//
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestBody Task task) throws CustomException {
        taskService.addTask(task);
    }

//    public void updateTask()

    @DeleteMapping("{taskID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String taskID) throws CustomException {
        taskService.deleteTask(taskID);
    }

    @PutMapping("discard/{userID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void discardTask(@PathVariable String userID, @RequestParam String taskID) throws CustomException {
        taskService.discardTask(taskID, userID);
    }

    // ... archive?userid=fsgdgsg&taskid=dfsgsfdfd
    // ... archive/user/fafaff?taskid=gsvcscs
    @PutMapping("archive/{userID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void archiveTask(@PathVariable String userID, @RequestParam String taskID){
        taskService.archiveTask(taskID, userID);
    }

    @GetMapping("{userID}")
    public List<Task> getAllUserTasks(@PathVariable String userID) throws CustomException {
        return taskService.getAllUserTasks(userID);
    }

    @GetMapping("taskof/{userID}")
    public Task getOneUserTask(@PathVariable String userID, @RequestParam String taskID) throws CustomException {
        return taskService.getOneUserTask(taskID, userID);
    }

    @GetMapping("new/old/{userID}")
    public List<Task> sortFromNewToOld(@PathVariable String userID){
        return taskService.sortFromNewToOld(userID);
    }

    @GetMapping("old/new/{userID}")
    public List<Task> sortFromOldToNew(@PathVariable String userID){
        return taskService.sortFromOldToNew(userID);
    }

    @GetMapping("farthest/{userID}")
    public List<Task> sortFromFarthest(@PathVariable String userID){
        return taskService.sortFromFarthest(userID);
    }

    @GetMapping("nearest/{userID}")
    public List<Task> sortFromNearest(@PathVariable String userID){
        return taskService.sortFromNearest(userID);
    }

    @GetMapping("before/{userID}")
    public List<Task> getAllBefore(@PathVariable String userID, @RequestParam LocalDateTime date){
        return taskService.getAllBefore(date, userID);
    }

    @GetMapping("after/{userID}")
    public List<Task> getAllAfter(@PathVariable String userID, @RequestParam LocalDateTime date){
        return taskService.getAllAfter(date, userID);
    }

    @GetMapping("between/{userID}")
    public List<Task> getAllBetween(@PathVariable String userID, @RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
        return taskService.getAllBetween(start, end, userID);
    }

    @GetMapping("archived/{userID}")
    public List<Task> getAllArchived(@PathVariable String userID){
        return taskService.getAllArchived(userID);
    }

    @GetMapping("discarded/{userID}")
    public List<Task> getAllDiscarded(@PathVariable String userID){
        return taskService.getAllDiscarded(userID);
    }

    @GetMapping("labeled/{userID}")
    public List<Task> getAllUnderLabel(@PathVariable String userID, @RequestParam Label label){
        return taskService.getAllUnderLabel(label, userID);
    }

}
