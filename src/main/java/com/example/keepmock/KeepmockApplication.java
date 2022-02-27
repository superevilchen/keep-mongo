package com.example.keepmock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class KeepmockApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeepmockApplication.class, args);
	}

}
