package me.vitalijZarscikov.booksBonusTaskAPI.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Books {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private int publicationYr;
    private int rating;

    public Books() {}

    public Books(Long id, String tittle, String author, int publicationYr, int rating) {
        this.id = id;
        this.title = tittle;
        this.author = author;
        this.publicationYr = publicationYr;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYr() {
        return publicationYr;
    }

    public void setPublicationYr(int publicationYr) {
        this.publicationYr = publicationYr;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
