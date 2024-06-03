package com.example.comparus.testtask.repository.impl;

import com.example.comparus.testtask.data.dto.UserSearchFilter;
import com.example.comparus.testtask.data.entity.User;
import com.example.comparus.testtask.properties.DataSourceConfigurationProperties;
import com.example.comparus.testtask.repository.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
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

        List<String> conditions = new ArrayList<>();

        if (filter.getId() != null) {
            conditions.add(condition(filter.getId(), columnMapping.getId()));
        }
        if (filter.getUsername() != null) {
            conditions.add(condition(filter.getUsername(), columnMapping.getUsername()));
        }
        if (filter.getName() != null) {
            conditions.add(condition(filter.getName(), columnMapping.getName()));
        }
        if (filter.getSurname() != null) {
            conditions.add(condition(filter.getSurname(), columnMapping.getSurname()));
        }

        if (!conditions.isEmpty()) {
            queryBuilder.append(" WHERE ")
                    .append(String.join(" AND ", conditions));
        }


        return queryBuilder.toString();
    }

    private String condition(String filterValue, String columnName) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("( LOWER(").append(columnName).append(") LIKE '%").append(filterValue).append("%')");
        return queryBuilder.toString();
    }
}
