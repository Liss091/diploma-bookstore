package com.redfox.services.impl;

import com.redfox.dao.BookDao;
import com.redfox.dao.GenreDao;
import com.redfox.domain.Book;
import com.redfox.domain.Genre;
import com.redfox.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private BookDao bookDao;

    private GenreDao genreDao;

    @Autowired
    public SearchServiceImpl(BookDao bookDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.genreDao = genreDao;
    }

    @Override
    public List<Book> findByCriteria(String criteria) {
        if (criteria == null || criteria.isEmpty()) {
            fetchAllBooks();
        }
        return bookDao.findByCriteria(criteria);
    }

    @Override
    public List<Book> fetchAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public List<Genre> fetchAllRelevantGenres() {
        return genreDao.findAllRelevantGenres();
    }

    @Override
    public List<Book> findByExtendedCriteria(String title, String authorNames, String isbn, List<String> genreIds,
                                             BigDecimal minPrice, BigDecimal maxPrice) {
        return bookDao.findByExtendedSearchCriterias(title, authorNames, isbn, genreIds, minPrice, maxPrice);
    }
}
