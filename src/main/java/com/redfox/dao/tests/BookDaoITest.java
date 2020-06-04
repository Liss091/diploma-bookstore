package com.redfox.dao.tests;

import com.redfox.dao.BookDao;
import com.redfox.domain.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:config/applicationContext.xml")
public class BookDaoITest {

    @Autowired
    private BookDao testObject;

    @Test
    public void testFindAll() {
        List<Book> books = testObject.findAll();

        Assert.assertEquals(1091, books.size());
    }

    @Test
    public void testFindByCriteria() {
        List<Book> books = testObject.findByCriteria("Programming Agile Mary Poppendieck, Highsmith");

        Assert.assertNotNull(books);
        Assert.assertEquals(1, books.size());
        Assert.assertEquals(1L, books.get(0).getId().longValue());
    }

    @Test
    public void testSave() {
        Book book = new Book();
        book.setId(2000);
        book.setTitle("Title");

        testObject.save(book);
    }


}
