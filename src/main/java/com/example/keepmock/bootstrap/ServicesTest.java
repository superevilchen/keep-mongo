package com.example.keepmock.bootstrap;

import com.example.keepmock.beans.Color;
import com.example.keepmock.beans.Label;
import com.example.keepmock.beans.Task;
import com.example.keepmock.beans.User;
import com.example.keepmock.exceptions.CustomException;
import com.example.keepmock.repos.UserRepository;
import com.example.keepmock.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ServicesTest implements CommandLineRunner {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = User.builder().id("621b86621e1b3b14501f029f").email("matan@gmail.com").password("12345").build();

        Label label1 = new Label("family", Color.BLUE);
        Label label2 = new Label("kkkff", Color.CRIMSON);

        Task task1 = Task.builder().id("621a95f6aea0f21c381650d4").isArchived(true).label(label1).text("amamama").user(user1).dueDate(LocalDateTime.of(2022, 12, 1 ,22, 00)).addedAt(LocalDateTime.now()).build();
        Task task2 = Task.builder().text("tsk2").user(user1).isDiscarded(true).label(label2).dueDate(LocalDateTime.of(2023, 1, 17, 19, 30)).addedAt(LocalDateTime.now()).build();

        userRepository.save(user1);
//        // add
        taskService.addTask(task1);
        taskService.addTask(task2);

//        // update
//        task1.setText("mamfnfr");
//        taskService.updateTask("621a95f6aea0f21c381650d4", task1);
//
//        // change status
//
//        taskService.changeTaskStatus("621a95f6aea0f21c381650d4", "isDiscarded");

//        // user all tasks
//        System.out.println("user all tasks: " + taskService.getAllUserTasks("621b86621e1b3b14501f029f"));
//
//        // get one
//        System.out.println("user one task: " + taskService.getOneUserTask("621a95f6aea0f21c381650d4"));
//
//        //sort
//        System.out.println("sort asc: " + taskService.sort("621b86621e1b3b14501f029f", false, "text"));
//
//        // get all before
//        System.out.println("before: " + taskService.getAllBefore(LocalDateTime.of(2022, 12, 2 ,22, 00), "621b86621e1b3b14501f029f"));
//
//        // get all after
//        System.out.println("after: " + taskService.getAllAfter(LocalDateTime.of(2023, 1, 16, 19, 30), "621b86621e1b3b14501f029f"));
//
        // get all between
        System.out.println("between: " + taskService.getAllBetween(LocalDateTime.of(2022, 12, 2 ,22, 00), LocalDateTime.of(2023, 1, 16, 19, 30), "621b86621e1b3b14501f029f"));

        // field
        System.out.println("all per field: " + taskService.getAllPerFieldStatus("621b86621e1b3b14501f029f", true, "isDiscarded") );

        // label
        System.out.println("all per label: " + taskService.getAllUnderLabel(label1, "621b86621e1b3b14501f029f"));
    }
}
