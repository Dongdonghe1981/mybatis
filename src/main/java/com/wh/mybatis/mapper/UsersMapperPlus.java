package com.wh.mybatis.mapper;

import com.wh.mybatis.dto.Users;

public interface UsersMapperPlus {

    Users getUserById(Integer id);
    Users getUserAndDept(Integer id);
    Users getUserByIdStep(Integer id);

}
