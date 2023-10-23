package com.project.pescueshop.repository.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@NoArgsConstructor
@AllArgsConstructor
public class BaseDAO {
    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate;
    @PersistenceContext
    public EntityManager entityManager;
}
