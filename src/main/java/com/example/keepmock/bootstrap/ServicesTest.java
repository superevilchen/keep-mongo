package com.example.keepmock.bootstrap;

import com.example.keepmock.beans.Color;
import com.example.keepmock.beans.Label;
import com.example.keepmock.beans.Task;
import com.example.keepmock.beans.User;
import com.example.keepmock.exceptions.CustomException;
import com.example.keepmock.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
@RequiredArgsConstructor
public class ServicesTest implements CommandLineRunner {

    private final TaskService taskService;
    @Override
    public void run(String... args) throws Exception {

        try {

            User user1 = User.builder().email("rkkfka@gmail.com").password("12345").build();

            Task task1 = Task.builder().id("621a95f6aea0f21c381650d4").label(new Label("matannan", Color.BROWN)).text("amamama").dueDate(LocalDateTime.of(2022, 12, 1 ,22, 00)).build();

            taskService.addTask(task1);

            taskService.deleteTask("1");


        } catch (CustomException e){
            System.out.println(e.toString());
        }

    }
}
