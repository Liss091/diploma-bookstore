package com.redfox.services;

import com.redfox.domain.Book;
import com.redfox.domain.Genre;

import java.math.BigDecimal;
import java.util.List;

public interface SearchService {

    List<Book> findByCriteria(String criteria);

    List<Book> findByExtendedCriteria(String title, String authorNames, String isbn, List<String> genreIds, BigDecimal minPrice, BigDecimal maxPrice);

    List<Book> fetchAllBooks();

    List<Genre> fetchAllRelevantGenres();
}
