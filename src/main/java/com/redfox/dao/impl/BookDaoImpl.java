package com.redfox.dao.impl;

import com.redfox.dao.BookDaoCustom;
import com.redfox.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class BookDaoImpl implements BookDaoCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Book> findByExtendedSearchCriterias(String title, String authorNames, List<String> genreIds,
                                                    BigDecimal minPrice, BigDecimal maxPrice) {
        StringBuilder builder = new StringBuilder("SELECT DISTINCT b.* FROM books b " +
                "LEFT JOIN book_authors ba ON b.book_id=ba.book_id " +
                "LEFT JOIN authors au ON ba.author_id=au.author_id " +
                "LEFT JOIN book_genres bg ON bg.book_id=b.book_id " +
                "LEFT JOIN genres g ON bg.genre_id=g.genre_id " +
                "WHERE ");
        builder.append("b.price BETWEEN :minPrice AND :maxPrice ");
        if (title != null && !title.isEmpty()) {
            builder.append("AND b.title_vector @@ plainto_tsquery(:title) ");
        }
        if (authorNames != null && !authorNames.isEmpty()) {
            builder.append("AND au.name_vector @@ plainto_tsquery(:authorNames) ");
        }
        if (!genreIds.isEmpty()) {
            builder.append("AND g.genre_id in (");
            for (int i = 0; i < genreIds.size(); i++) {
                builder.append(":id" + i);
                if (i != genreIds.size()-1) {
                    builder.append(", ");
                }
            }
            builder.append(") ");
        }

        Query query = entityManager.createNativeQuery(builder.toString(), Book.class);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        if (title != null && !title.isEmpty()) {
            query.setParameter("title", title);
        }
        if (authorNames != null && !authorNames.isEmpty()) {
            query.setParameter("authorNames", authorNames);
        }
        if (!genreIds.isEmpty()) {
            for (int i = 0; i < genreIds.size(); i++) {
                query.setParameter("id" + i, Integer.parseInt(genreIds.get(i)));
            }
        }
        return query.getResultList();
    }
}
