package com.wh.mybatis.mapper;

import com.wh.mybatis.dto.Film;

import java.util.List;

public interface FilmMapper{
     List<Film> getFilm(Film film);

     List<Film> getFilmTrim(Film film);
}