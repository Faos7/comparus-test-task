package com.example.comparus.testtask.repository;

import com.example.comparus.testtask.data.dto.UserSearchFilter;
import com.example.comparus.testtask.data.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findUsersByFilter(UserSearchFilter filter);
}
