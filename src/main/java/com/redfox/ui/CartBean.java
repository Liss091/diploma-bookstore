package com.redfox.ui;

import com.redfox.domain.Book;
import com.redfox.services.OrderService;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Named
@SessionScoped
public class CartBean implements Serializable {

    private BigDecimal totalAmount;

    private Map<Book, Integer> orderedBooksCount;

    private Set<Book> orderedBooks;

    @Inject
    private OrderService orderService;

    @PostConstruct
    public void init() {
        orderedBooks = new HashSet<>();
        orderedBooksCount = new HashMap<>();
        totalAmount = BigDecimal.ZERO;
    }

    public void addToCart(Book book) {
        orderedBooksCount.putIfAbsent(book, 1);
        orderedBooksCount.computeIfPresent(book, (key, val) -> val++);
        orderedBooks.add(book);
        totalAmount = totalAmount.add(book.getPrice());
        showMessageAddToCart(book.getTitle());
    }

    public void countTotalAmount() {
        totalAmount = BigDecimal.ZERO;
        for (Map.Entry<Book, Integer> entry : orderedBooksCount.entrySet()) {
            Book key = entry.getKey();
            Integer value = entry.getValue();
            totalAmount = totalAmount.add(key.getPrice().multiply(BigDecimal.valueOf(value)));
        }
    }

    public void showMessageAddToCart(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Book is added to your cart",  msg) );
    }

    public Map<Book, Integer> getOrderedBooksCount() {
        return orderedBooksCount;
    }

    public void setOrderedBooksCount(Map<Book, Integer> orderedBooks) {
        this.orderedBooksCount = orderedBooks;
    }

    public Set<Book> getOrderedBooks() {
        return orderedBooks;
    }

    public void setOrderedBooks(Set<Book> orderedBooks) {
        this.orderedBooks = orderedBooks;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
