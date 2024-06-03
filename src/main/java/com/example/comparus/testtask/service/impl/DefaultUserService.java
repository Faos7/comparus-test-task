package com.example.comparus.testtask.service.impl;

import com.example.comparus.testtask.data.dto.UserSearchFilter;
import com.example.comparus.testtask.repository.UserDao;
import com.example.comparus.testtask.service.UserService;
import com.example.comparus.testtask.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final List<UserDao> userDaoList;

    private final UserMapper userMapper;

    @Override
    public List<com.example.comparus.testtask.data.dto.UserData> getUserDataList(UserSearchFilter filter) {
        return userDaoList.parallelStream()
                .map(userDao -> userDao.findUsersByFilter(filter))
                .flatMap(List::stream)
                .map(userMapper::map)
                .toList();
    }
}
