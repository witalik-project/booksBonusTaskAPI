package me.vitalijZarscikov.booksBonusTaskAPI.controllers;

import me.vitalijZarscikov.booksBonusTaskAPI.entities.Books;
import me.vitalijZarscikov.booksBonusTaskAPI.repositories.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class BooksAPIIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BooksRepository booksRepository;

    @BeforeEach
    public void setup() {
        booksRepository.deleteAll();
        booksRepository.save(new Books(null, "Test Book 1", "Test Author 1", 2000, 4));
        booksRepository.save(new Books(null, "Test Book 2", "Test Author 2", 2010, 3));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test Book 1"))
                .andExpect(jsonPath("$[1].title").value("Test Book 2"))
                .andDo(print());
    }

    @Test
    public void testFilterBooksByTitle() throws Exception {
        mockMvc.perform(get("/api/books/filter")
                        .param("title", "Test Book 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Book 1"))
                .andDo(print());
    }

    @Test
    public void testFilterBooksByAuthor() throws Exception {
        mockMvc.perform(get("/api/books/filter")
                        .param("author", "Test Author 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("Test Author 1"))
                .andDo(print());
    }

    @Test
    public void testRateBook() throws Exception {
        Long bookId = booksRepository.findAll().get(0).getId();

        mockMvc.perform(post("/api/books/rate")
                        .param("id", bookId.toString())
                        .param("rating", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Rated"))
                .andDo(print());

        // Verify the rating is updated
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(5))
                .andDo(print());
    }

    @Test
    public void testRateBookInvalidRating() throws Exception {
        Long bookId = booksRepository.findAll().get(0).getId();

        mockMvc.perform(post("/api/books/rate")
                        .param("id", bookId.toString())
                        .param("rating", "11")  // Invalid rating
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Can be rated from 0 to 10"))
                .andDo(print());
    }
}
