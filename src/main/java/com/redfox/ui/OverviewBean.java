package com.redfox.ui;

import com.redfox.domain.Book;
import com.redfox.services.BooksService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class OverviewBean implements Serializable {

    @Inject
    private BooksService booksService;

    private List<Book> topRatedBooks;

    @PostConstruct
    public void init() {
        topRatedBooks = booksService.topRatedBooks();
    }

    public List<Book> getTopRatedBooks() {
        return topRatedBooks;
    }

    public void setTopRatedBooks(List<Book> topRatedBooks) {
        this.topRatedBooks = topRatedBooks;
    }

    public void setBooksService(BooksService booksService) {
        this.booksService = booksService;
    }
}
