package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
@EnableTransactionManagement
public class StudentConfig {
   @Bean
   CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
          Student mariam =   new Student(
                    1L,
                    "Mariam",
                    "main@gmai.com",
                    LocalDate.of(2000, APRIL, 20)
            );
            Student john =   new Student(
                    2L,
                    "John Doe",
                    "jogn@gmai.com",
                    LocalDate.of(2000, JANUARY, 20)
            );

            repository.saveAll(
                    List.of(mariam, john)
            );
        };
    }
}
