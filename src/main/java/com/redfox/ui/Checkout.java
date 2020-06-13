package com.redfox.ui;

import com.redfox.domain.Book;
import com.redfox.domain.Order;
import com.redfox.services.OrderService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Named
@RequestScoped
public class Checkout implements Serializable {

    private String firstName;

    private String lastName;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String phoneNumber;

    private String email;

    private BigDecimal totalAmount;

    private Set<Book> bookList;

    private List<String> deliveryMethods;

    private String selectedDeliveryMethod;

    @PostConstruct
    public void init() {
        deliveryMethods = new ArrayList<>();
        deliveryMethods.add("Nova Poshta");
        deliveryMethods.add("Express Delivery");
        deliveryMethods.add("DHL Delivery");
    }

    @Inject
    private OrderService orderService;

    public String confirmOrder() {
        Order order = new Order();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(addressLine1.concat(" ").concat(addressLine2).concat(" ").concat(addressLine3));
        order.setPhoneNumber(phoneNumber);
        order.setEmail(email);
        order.setAmount(totalAmount);
        order.setDelivery(selectedDeliveryMethod);
        order.setCreationDate(new Timestamp(System.currentTimeMillis()));
        order.setBooksList(new ArrayList<>(bookList));

        orderService.saveOrder(order);

        return "orderSuccess";
    }

    public String returnToHomePage() {
        return "index";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<Book> getBookList() {
        return bookList;
    }

    public void setBookList(Set<Book> bookList) {
        this.bookList = bookList;
    }

    public List<String> getDeliveryMethods() {
        return deliveryMethods;
    }

    public void setDeliveryMethods(List<String> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }

    public String getSelectedDeliveryMethod() {
        return selectedDeliveryMethod;
    }

    public void setSelectedDeliveryMethod(String selectedDeliveryMethod) {
        this.selectedDeliveryMethod = selectedDeliveryMethod;
    }
}
