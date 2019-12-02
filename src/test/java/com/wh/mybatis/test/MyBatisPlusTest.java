package com.wh.mybatis.test;

import com.wh.mybatis.dto.Users;
import com.wh.mybatis.mapper.UsersMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisPlusTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            UsersMapperPlus mapper = openSession.getMapper(UsersMapperPlus.class);
            Users user = mapper.getUserById(1);
            System.out.println(user);
        }
    }

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            UsersMapperPlus mapper = openSession.getMapper(UsersMapperPlus.class);
            Users user = mapper.getUserAndDept(1);
            System.out.println(user);
            System.out.println(user.getDepartment());
        }
    }

    @Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            UsersMapperPlus mapper = openSession.getMapper(UsersMapperPlus.class);
            Users user = mapper.getUserByIdStep(1);
            System.out.println(user);
            System.out.println(user.getDepartment());
        }
    }
}
