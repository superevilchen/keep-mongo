package com.example.keepmock.bootstrap;

import com.example.keepmock.beans.Color;
import com.example.keepmock.beans.Label;
import com.example.keepmock.beans.Task;
import com.example.keepmock.beans.User;
import com.example.keepmock.repos.MongoTemplateImpl;
import com.example.keepmock.repos.TaskRepository;
import com.example.keepmock.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReposTest implements CommandLineRunner {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MongoTemplateImpl mongoTemplate;

    @Override
    public void run(String... args) throws Exception {

        User user1 = User.builder().id("621b86621e1b3b14501f029f").email("matan@gmail.com").password("12345").build();
        userRepository.save(user1);

        Label label1 = new Label("family", Color.BLUE);
        Label label2 = new Label("kkkff", Color.CRIMSON);


        Task task1 = Task.builder().id("621a95f6aea0f21c381650d4").isArchived(true).label(label1).text("amamama").user(user1).dueDate(LocalDateTime.of(2022, 12, 1 ,22, 00)).addedAt(LocalDateTime.now()).build();
        Task task2 = Task.builder().text("tsk2").user(user1).isDiscarded(true).label(label2).dueDate(LocalDateTime.of(2023, 1, 17, 19, 30)).addedAt(LocalDateTime.now()).build();

        taskRepository.saveAll(List.of(task1, task2));

//        System.out.println(taskRepository.findByUserId("621b86621e1b3b14501f029f"));
//
//        mongoTemplate.discardTask("621a95f6aea0f21c381650d4", "621b86621e1b3b14501f029f");
//
//        System.out.println("farthest: " + mongoTemplate.sortFromFarthest("621b86621e1b3b14501f029f"));
//
//        System.out.println("nearest: " + mongoTemplate.sortFromNearest("621b86621e1b3b14501f029f"));
//
//        System.out.println("new to old: " + mongoTemplate.sortFromNewToOld("621b86621e1b3b14501f029f"));
//
//        System.out.println("old to new: " + mongoTemplate.sortFromOldToNew("621b86621e1b3b14501f029f"));
//
//        System.out.println("under label" + taskRepository.findByLabelAndUserId(label1, "621b86621e1b3b14501f029f"));

//        System.out.println("before: " + taskRepository.findByDueDateLessThanAndUserId(LocalDateTime.of(2022, 12, 2 ,22, 00), "621b86621e1b3b14501f029f"));
//
//        System.out.println("after: " + taskRepository.findByDueDateGreaterThanAndUserId(LocalDateTime.of(2023, 1, 16, 19, 30),"621b86621e1b3b14501f029f"));

//        System.out.println("all discarded: " + mongoTemplate.getAllDiscarded("621b86621e1b3b14501f029f"));
//
//        System.out.println("all archived: " + mongoTemplate.getAllArchived("621b86621e1b3b14501f029f"));
    }
}
