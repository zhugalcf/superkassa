package com.zhugalcf.superkassa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class SuperkassaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperkassaApplication.class, args);
	}
}
