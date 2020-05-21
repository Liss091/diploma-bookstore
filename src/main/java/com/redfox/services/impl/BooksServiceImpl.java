package com.redfox.services.impl;

import com.redfox.dao.BookDao;
import com.redfox.domain.Book;
import com.redfox.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServiceImpl implements BooksService {

    private BookDao bookDao;

    @Autowired
    public BooksServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> fetchAllBooks() {
        return bookDao.findAll();
    }
}
