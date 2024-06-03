package com.example.comparus.testtask.repository.impl;

import com.example.comparus.testtask.data.dto.UserSearchFilter;
import com.example.comparus.testtask.data.entity.User;
import com.example.comparus.testtask.properties.DataSourceConfigurationProperties;
import com.example.comparus.testtask.repository.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MultitenantUserDao implements UserDao {
    private final EntityManager                                   em;
    private final DataSourceConfigurationProperties.ColumnMapping columnMapping;
    private final String                                          tableName;

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findUsersByFilter(UserSearchFilter filter) {
        Query query = em.createNativeQuery(generateQuery(filter), User.class);
        return query.getResultList();
    }

    private String generateQuery(UserSearchFilter filter) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        queryBuilder.append(columnMapping.getId()).append(" AS id, ");
        queryBuilder.append(columnMapping.getUsername()).append(" AS username, ");
        queryBuilder.append(columnMapping.getName()).append(" AS name, ");
        queryBuilder.append(columnMapping.getSurname()).append(" AS surname");

        queryBuilder.append(" FROM ");
        queryBuilder.append(tableName);

        if (filter.getId() != null) {
            queryBuilder.append(" WHERE ").append(condition(filter.getId(), columnMapping.getId()));
            if (filter.getUsername() != null) {
                queryBuilder.append(" AND ").append(condition(filter.getUsername(), columnMapping.getUsername()));
            }
            if (filter.getName() != null) {
                queryBuilder.append(" AND ").append(condition(filter.getName(), columnMapping.getName()));
            }
            if (filter.getSurname() != null) {
                queryBuilder.append(" AND ").append(condition(filter.getSurname(), columnMapping.getSurname()));
            }
        } else if (filter.getUsername() != null) {
            queryBuilder.append(" WHERE ").append(condition(filter.getUsername(), columnMapping.getUsername()));
            if (filter.getName() != null) {
                queryBuilder.append(" AND ").append(condition(filter.getName(), columnMapping.getName()));
            }
            if (filter.getSurname() != null) {
                queryBuilder.append(" AND ").append(condition(filter.getSurname(), columnMapping.getSurname()));
            }
        } else if (filter.getName() != null) {
            queryBuilder.append(" WHERE ").append(condition(filter.getName(), columnMapping.getName()));
            if (filter.getSurname() != null) {
                queryBuilder.append(" AND ").append(condition(filter.getSurname(), columnMapping.getSurname()));
            }
        } else if (filter.getSurname() != null) {
            queryBuilder.append(" WHERE ").append(condition(filter.getSurname(), columnMapping.getSurname()));
        }

        return queryBuilder.toString();
    }

    private String condition(String filterValue, String columnName) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("( LOWER(").append(columnName).append(") LIKE '%").append(filterValue).append("%')");
        return queryBuilder.toString();
    }
}
