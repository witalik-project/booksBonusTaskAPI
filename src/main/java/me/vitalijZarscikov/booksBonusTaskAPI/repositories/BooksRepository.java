package me.vitalijZarscikov.booksBonusTaskAPI.repositories;

import me.vitalijZarscikov.booksBonusTaskAPI.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    List<Books> findByTitleContaining(String title);
    List<Books> findByAuthorContaining(String author);
    List<Books> findByPublicationYr(int publicationYr);
    List<Books> findByRating(int rating);
}
