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
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);
            Film film = new Film();
            film.setFilmId(new Short("2"));
            film.setRating("G");
            List<Film> result = mapper.getFilmTrim(film);
            result.stream().forEach(System.out::println);
        }
    }

    @Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);
            Film film = new Film();
            film.setFilmId(new Short("3"));
            film.setRating("G");
            List<Film> result = mapper.getFilmChoose(film);
            result.stream().forEach(System.out::println);
        }
    }

    @Test
    public void test04() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);
            Film film = new Film();
            film.setFilmId((short)3);
            film.setRating("PG");
            film.setRentalDuration((byte) 5);
            mapper.updateFilm(film);
            openSession.commit();
        }
    }

    @Test
    public void test05() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);
            Film film = new Film();
            film.setFilmId((short)3);
            film.setRating("PG-13");
            mapper.updateFilmTrim(film);
            openSession.commit();
        }
    }

    @Test
    public void test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);
            List<Film> result = mapper.getFilmForEach(Arrays.asList(1, 2, 3));
            result.stream().forEach(System.out::println);
        }
    }

    @Test
    public void test07() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);
            List<Film> films = new ArrayList<>();
            Film film = new Film("ZORRO ARK1", (byte)1, (byte)1, BigDecimal.valueOf(4.99),BigDecimal.valueOf(18.99));
            films.add(film);
            film = new Film("ZORRO ARK2",(byte)1,(byte)3,BigDecimal.valueOf(4.99),BigDecimal.valueOf(18.99));
            films.add(film);
            mapper.addFilms2(films);
            openSession.commit();
        }
    }

    @Test
    public void test08() throws IOException {
        SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            FilmMapper mapper = openSession.getMapper(FilmMapper.class);
            Film film = new Film();
            film.setFilmId(new Short("2"));
            film.setRating("G");
            List<Film> result = mapper.getFilmParameter(film);
            result.stream().forEach(System.out::println);
        }
    }
}
