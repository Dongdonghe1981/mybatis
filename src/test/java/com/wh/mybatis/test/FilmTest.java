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
import java.time.LocalDate;
import java.util.List;

public class FilmTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);
            Film film = new Film();
            film.setFilmId(new Short("2"));
            film.setRating("G");
            List<Film> result = mapper.getFilm(film);
            result.stream().forEach(System.out::println);
        }
    }
}
