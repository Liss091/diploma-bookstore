package com.redfox.dao;

import com.redfox.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Integer>, BookDaoCustom {

    /**
     * Ищет книгу по названию, имени авторов и жанрам.
     *
     * @param searchCriteria строка из поля поиска
     * @return список книг, которые соответствуют критерии
     */
    @Query(value = "SELECT b.* FROM books b " +
            "JOIN full_search_vector fsv ON b.book_id = fsv.book_id " +
            "WHERE fsv.full_tsvector @@ plainto_tsquery(:criteria)",
    nativeQuery = true)
    List<Book> findByCriteria(@Param("criteria") String searchCriteria);


    @Query(value = "SELECT b.* FROM books b " +
            "WHERE title_vector @@ plainto_tsquery(:criteria);",
            nativeQuery = true)
    List<Book> findByTitleCriteria(@Param("criteria") String titleCriteria);


    @Query(value = "SELECT b.* FROM books b " +
            "JOIN book_authors ba ON b.book_id=ba.book_id " +
            "JOIN authors au ON ba.author_id=au.author_id " +
            "WHERE au.name_vector @@ plainto_tsquery(:criteria);",
            nativeQuery = true)
    List<Book> findByAuthorCriteria(@Param("criteria") String authorCriteria);

}
