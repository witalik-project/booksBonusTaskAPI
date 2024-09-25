package me.vitalijZarscikov.booksBonusTaskAPI;

import me.vitalijZarscikov.booksBonusTaskAPI.entities.Books;
import me.vitalijZarscikov.booksBonusTaskAPI.repositories.BooksRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BooksBonusTaskApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksBonusTaskApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(BooksRepository booksRepository) {
		return args -> {
			booksRepository.save(new Books(null, "Book Title 1", "Author 1", 2005, 2));
			booksRepository.save(new Books(null, "Book Title 2", "Author 2", 2007, 4));
			booksRepository.save(new Books(null, "Book Title 3", "Author 3", 2009, 3));
			booksRepository.save(new Books(null, "Book Title 4", "Author 3", 2009, 1));
			booksRepository.save(new Books(null, "Book Title 5", "Author 4", 2010, 1));
		};
	}

}
