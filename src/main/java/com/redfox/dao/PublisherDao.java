package com.redfox.dao;

import com.redfox.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherDao extends JpaRepository<Publisher, Integer> {
}
