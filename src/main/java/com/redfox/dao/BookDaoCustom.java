package com.redfox.dao;

import com.redfox.domain.Book;

import java.math.BigDecimal;
import java.util.List;

public interface BookDaoCustom {

    List<Book> findByExtendedSearchCriterias(String title, String authorNames, List<String> genreIds, BigDecimal minPrice, BigDecimal maxPrice);
}
