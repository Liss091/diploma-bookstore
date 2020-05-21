package com.redfox.services.impl;

import com.redfox.dao.BookDao;
import com.redfox.domain.Book;
import com.redfox.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private BookDao bookDao;

    @Autowired
    public SearchServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
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
}
