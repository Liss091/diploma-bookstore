package com.redfox.dao;

import com.redfox.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreDao extends JpaRepository<Genre, Integer> {

    @Query(value = "SELECT DISTINCT g.* FROM genres g " +
            "JOIN book_genres bg ON g.genre_id = bg.genre_id " +
            "JOIN books b ON b.book_id = bg.book_id",
    nativeQuery = true)
    List<Genre> findAllRelevantGenres();
}
