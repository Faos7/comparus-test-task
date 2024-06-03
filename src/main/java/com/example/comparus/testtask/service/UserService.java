package com.example.comparus.testtask.service;

import com.example.comparus.testtask.data.dto.UserData;
import com.example.comparus.testtask.data.dto.UserSearchFilter;

import java.util.List;

public interface UserService {
    List<UserData> getUserDataList(UserSearchFilter filter);
}
