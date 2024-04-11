package ru.framuga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class ReaderApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ReaderApplication.class);
        ReaderRepositoryJPA readerRepositoryJPA = context.getBean(ReaderRepositoryJPA.class);

        for (int i = 1; i < 10; i++) {
            Reader reader = new Reader();
            reader.setName("Reader #" + i);
            readerRepositoryJPA.save(reader);
        }
    }
}