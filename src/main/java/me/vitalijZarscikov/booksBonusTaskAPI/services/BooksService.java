package me.vitalijZarscikov.booksBonusTaskAPI.services;

import me.vitalijZarscikov.booksBonusTaskAPI.entities.Books;
import me.vitalijZarscikov.booksBonusTaskAPI.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    public List<Books> filterByTitle(String title) {
        return booksRepository.findByTitleContaining(title);
    }

    public List<Books> filterByAuthor(String author) {
        return booksRepository.findByAuthorContaining(author);
    }

    public List<Books> filterByYear(int publicationYr) {
        return booksRepository.findByPublicationYr(publicationYr);
    }

    public List<Books> filterByRating(int rating) {
        return booksRepository.findByRating(rating);
    }

    public Books rateBook(Long id, int rating) {
        Books book = booksRepository.findById(id).orElseThrow();
        book.setRating(rating);
        return booksRepository.save(book);
    }

}
