package com.redfox.ui;

import com.redfox.domain.Book;
import com.redfox.services.SearchService;
import javax.enterprise.context.SessionScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class DataGridView implements Serializable {

    private List<Book> allBooks;

    private List<Book> books;

    private Book selectedBook;

    private String searchCriteria;

    @Inject
    private SearchService searchService;

    @PostConstruct
    public void init() {
        allBooks = searchService.fetchAllBooks();
        books = allBooks;
    }

    public void findBooksByCriteria() {
        if (searchCriteria != null && !searchCriteria.isEmpty()){
            books = searchService.findByCriteria(searchCriteria);
        }
        else {
            books = allBooks;
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public List<Book> getAllBooks() {
        return allBooks;
    }

    public void setAllBooks(List<Book> allBooks) {
        this.allBooks = allBooks;
    }
}
