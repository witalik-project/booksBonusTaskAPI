package me.vitalijZarscikov.booksBonusTaskAPI.services;

import me.vitalijZarscikov.booksBonusTaskAPI.entities.Books;
import me.vitalijZarscikov.booksBonusTaskAPI.repositories.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BooksServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BooksService booksService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        // Mocking the repository behavior
        List<Books> mockBooks = Arrays.asList(
                new Books(1L, "Book 1", "Author 1", 2001, 4),
                new Books(2L, "Book 2", "Author 2", 2002, 5)
        );

        when(booksRepository.findAll()).thenReturn(mockBooks);

        // Call the method and assert
        List<Books> books = booksService.getAllBooks();
        assertEquals(2, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
        verify(booksRepository, times(1)).findAll();
    }

    @Test
    void filterByTitle_ShouldReturnFilteredBooks() {
        List<Books> mockBooks = Arrays.asList(new Books(1L, "Book 1", "Author 1", 2001, 4));
        when(booksRepository.findByTitleContaining("Book 1")).thenReturn(mockBooks);

        List<Books> books = booksService.filterByTitle("Book 1");
        assertEquals(1, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
        verify(booksRepository, times(1)).findByTitleContaining("Book 1");
    }

    @Test
    void filterByAuthor_ShouldReturnFilteredBooks() {
        List<Books> mockBooks = Arrays.asList(new Books(1L, "Book 1", "Author 1", 2001, 4));
        when(booksRepository.findByAuthorContaining("Author 1")).thenReturn(mockBooks);

        List<Books> books = booksService.filterByAuthor("Author 1");
        assertEquals(1, books.size());
        assertEquals("Author 1", books.get(0).getAuthor());
        verify(booksRepository, times(1)).findByAuthorContaining("Author 1");
    }

    @Test
    void rateBook_ShouldUpdateRating() {
        Books book = new Books(1L, "Book 1", "Author 1", 2001, 4);
        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));
        when(booksRepository.save(book)).thenReturn(book);

        Books updatedBook = booksService.rateBook(1L, 2);
        assertEquals(2, updatedBook.getRating());
        verify(booksRepository, times(1)).save(book);
    }

    @Test
    void rateBook_ShouldThrowException_WhenBookNotFound() {
        when(booksRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            booksService.rateBook(1L, 2);
        });

        assertEquals(NoSuchElementException.class, exception.getClass());
        verify(booksRepository, times(0)).save(any(Books.class));
    }
}
