package com.redfox.services;

import com.redfox.domain.Book;

import java.util.List;

public interface BooksService {

    List<Book> fetchAllBooks();

    List<Book> topRatedBooks();
}
