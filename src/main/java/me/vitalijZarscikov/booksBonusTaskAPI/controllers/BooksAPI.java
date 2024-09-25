package me.vitalijZarscikov.booksBonusTaskAPI.controllers;

import me.vitalijZarscikov.booksBonusTaskAPI.entities.Books;
import me.vitalijZarscikov.booksBonusTaskAPI.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/books")
public class BooksAPI {

    @Autowired
    private BooksService booksService;

    @GetMapping
    public List<Books> getAllBooks() {
        return booksService.getAllBooks();
    }

    @GetMapping(path = "/filter")
    public Set<Books> filterBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Integer publicationYr
    ) {
        Set<Books> books = new HashSet<>();

        if(title != null)  books.addAll(new HashSet<>(booksService.filterByTitle(title)));
        if(author != null) books.addAll(new HashSet<>(booksService.filterByAuthor(author)));
        if(publicationYr != null) books.addAll(new HashSet<>(booksService.filterByYear(publicationYr)));
        if(rating != null) books.addAll(new HashSet<>(booksService.filterByRating(rating)));

        books = books.stream()
                .filter(book -> title == null || book.getTitle().equalsIgnoreCase(title))
                .filter(book -> author == null || book.getAuthor().equalsIgnoreCase(author))
                .filter(book -> rating == null || book.getRating() == rating)
                .filter(book -> publicationYr == null || book.getPublicationYr() == publicationYr)
                .collect(Collectors.toSet());

        return books;
    }

    @PostMapping(path = "/rate")
    public String rateBook(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "rating") Integer rating
    ) {
        if(rating == null) return "Rating do not changed";
        if(rating < 0 || rating > 10) return "Can be rated from 0 to 10";
        booksService.rateBook(id, rating);
        return "Rated";
    }
}
