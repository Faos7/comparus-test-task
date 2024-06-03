package com.example.comparus.testtask.utils.mappers;

import com.example.comparus.testtask.data.dto.UserData;
import com.example.comparus.testtask.data.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserData map(User user);
}
