package com.wh.mybatis.mapper;

import com.wh.mybatis.dto.Department;

import java.util.List;

public interface DepartmentMapper {
    Department getDeptById(Integer id);
    List<Department> getDeptByIdPlus(Integer id);
    Department getDeptByIdStep(Integer id);
}
