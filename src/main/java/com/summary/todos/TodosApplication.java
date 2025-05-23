package com.summary.todos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodosApplication {

	public static void main(String[] args) {
		System.out.println("SUPABASE_URL: " + System.getenv("SUPABASE_URL"));
		System.out.println("SUPABASE_PASSWORD: " + System.getenv("SUPABASE_PASSWORD"));
        
		System.out.println("SLACK_WEBHOOK_URL: " + System.getenv("SLACK_WEBHOOK_URL"));
		System.out.println("COHERE_API_KEY: " + System.getenv("COHERE_API_KEY"));

 
		SpringApplication.run(TodosApplication.class, args);
	}

}
