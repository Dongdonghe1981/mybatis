package com.wh.mybatis.mapper;

import com.wh.mybatis.dto.Users;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * mybatis允许定义以下类型的返回值
 * 	Integer,Long,Boolean,void
 */
public interface UsersMapper {
	//多条记录封装成一个map,Map<Integer,Users>,key是主键，值是Bean
	@MapKey("id")//设定Bean的哪个属性作为key
	Map<Integer,Users> selectUsersByUserNameReturnMap(String userName);
	//返回一条记录的map,key是列名
	Map<String,Object> getUserByIdReturnMap(Integer id);
	List<Users> selectUsersByUserName(String userName);
	Users selectUserByMap(Map<String,Object> map);
	Users selectUserByIdAndUserName(@Param("id") Integer id, @Param("userName") String userName);
	Users selectUser(Integer id);
	Integer addUser(Users u);
	Integer updateUser(Users u);
	Integer deleteUser(Integer id);

}
