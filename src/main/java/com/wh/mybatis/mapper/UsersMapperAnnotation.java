package com.wh.mybatis.mapper;

import com.wh.mybatis.dto.Users;
import org.apache.ibatis.annotations.Select;

public interface UsersMapperAnnotation {
    @Select("select * from users where id = #{id}")
    Users selectUser(Integer id);
}
