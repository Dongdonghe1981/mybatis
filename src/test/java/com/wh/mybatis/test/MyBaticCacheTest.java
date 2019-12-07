package com.wh.mybatis.test;

import com.wh.mybatis.dto.Film;
import com.wh.mybatis.dto.Users;
import com.wh.mybatis.mapper.FilmMapper;
import com.wh.mybatis.mapper.UsersMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBaticCacheTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public  void test01() throws IOException {
        Film result1;
        Film result2;
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession(true)) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);

            List<Film> list = mapper.getAllFilm();
            result1 = mapper.getFilmById((short)1);
            openSession.clearCache();
            result2 = mapper.getFilmById((short)1);
            System.out.println(result1 == result2);
        }
    }

    @Test
    public  void testSecondLevelCache() throws IOException {
        Film result1;
        Film result2;
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession(true)) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);

            result1 = mapper.getFilmById((short)1);
            result2 = mapper.getFilmById((short)1);
            System.out.println(result1 == result2);
        }
    }

}

