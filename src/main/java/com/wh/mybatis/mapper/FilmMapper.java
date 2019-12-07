package com.wh.mybatis.mapper;

import com.wh.mybatis.dto.Film;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilmMapper{
     List<Film> getFilm(Film film);

     List<Film> getFilmTrim(Film film);

     List<Film> getFilmChoose(Film film);

     List<Film> getFilmForEach(@Param("ids") List ids);

     void updateFilm(Film film);

     void updateFilmTrim(Film film);

     void addFilms(@Param("films") List<Film> films);
     void addFilms2(@Param("films") List<Film> films);

     List<Film> getFilmParameter(Film film);
}