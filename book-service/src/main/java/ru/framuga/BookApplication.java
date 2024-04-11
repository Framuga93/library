package ru.framuga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BookApplication.class);
        BookRepositoryJPA bookRepositoryJPA = context.getBean(BookRepositoryJPA.class);

        for (int i = 1; i < 10; i++) {
            Book book = new Book();
            book.setName("Book #" + i);
            bookRepositoryJPA.save(book);
        }


    }
}