package com.redfox.dao.tests;

import com.redfox.dao.BookDao;
import com.redfox.dao.OrderDao;
import com.redfox.domain.Book;
import com.redfox.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:config/applicationContext.xml")
public class OrderDaoITest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderDao orderDao;

    @Test
    @Rollback
    public void save() {
        Book book = bookDao.findById(1).get();

        Order order = new Order();
        order.setFirstName("F");
        order.setLastName("L");
        order.setAddress("123");
        order.setAmount(BigDecimal.TEN);
        order.setCreationDate(new Timestamp(System.currentTimeMillis()));
        order.setBooksList(new ArrayList<>(Arrays.asList(book)));

        orderDao.save(order);

        bookDao.findAll();
    }
}
