package com.redfox.services;

import com.redfox.domain.Book;

import java.util.List;

public interface SearchService {

    List<Book> findByCriteria(String criteria);

    List<Book> fetchAllBooks();
}
