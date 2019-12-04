package com.wh.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wh.mybatis.mapper.UsersMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.wh.mybatis.dto.Users;
import com.wh.mybatis.mapper.UsersMapper;

public class MyBatisTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	@Test
	public void test01() throws IOException {

		SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
		try(SqlSession openSession = sqlSessionFactory.openSession()) {
			Users user = openSession.selectOne("com.wh.mybatis.mapper.UsersMapper.selectUser", 1);
			System.out.println(user);
		}
	}
	
	@Test
	public void test02() throws IOException {
		
		SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
		
		try(SqlSession openSession = sqlSessionFactory.openSession()){
			UsersMapper mapper = openSession.getMapper(UsersMapper.class);
			Users user = mapper.selectUser(1);;
			System.out.println(user);
		}
	}

	@Test
	public void test03() throws IOException {

		SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();
		try(SqlSession openSession = sqlSessionFactory.openSession()){
			UsersMapperAnnotation mapper =  openSession.getMapper(UsersMapperAnnotation.class);
			Users user = mapper.selectUser(1);;
			System.out.println(user);

		}
	}

	/**
	 * 手动提交
	 * sqlSessionFactory.openSession()
	 * 自动提交
	 * sqlSessionFactory.openSession(true)
	 * @throws IOException
	 */
	@Test
	public void test04() throws IOException {
		SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();

		try(SqlSession openSession = sqlSessionFactory.openSession(true)){
			UsersMapper mapper =  openSession.getMapper(UsersMapper.class);
			// 添加数据，返回自增主键值
			Users u = new Users("user1","111","boy","nik1");
			mapper.addUser(u);
			//更新，返回更新的记录数
			//int i = mapper.updateUser(new Users( 2,"user1","222","boy","nik1"));
			System.out.println(u.getId());
			//删除
			//mapper.deleteUser(28);
		}
	}

	@Test
	public  void test05() throws IOException {
		SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();

		try(SqlSession openSession = sqlSessionFactory.openSession(true)) {
			UsersMapper mapper = openSession.getMapper(UsersMapper.class);
			Users u = mapper.selectUserByIdAndUserName(33,"user1");
			System.out.println(u);
		}
	}

	@Test
	public  void test06() throws IOException {
		SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();

		try(SqlSession openSession = sqlSessionFactory.openSession(true)) {
			UsersMapper mapper = openSession.getMapper(UsersMapper.class);
			Map<String,Object> map = new HashMap<>();
			map.put("id",33);
			map.put("userName","user1");
			Users u = mapper.selectUserByMap(map);
			System.out.println(u);
		}
	}

	@Test
	public  void test07() throws IOException {
		SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();

		try(SqlSession openSession = sqlSessionFactory.openSession(true)) {
			UsersMapper mapper = openSession.getMapper(UsersMapper.class);
			List<Users> datas = mapper.selectUsersByUserName("user1");
			datas.stream().forEach(System.out :: println);
		}
	}

	@Test
	public  void test08() throws IOException {
		SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();

		try(SqlSession openSession = sqlSessionFactory.openSession(true)) {
			UsersMapper mapper = openSession.getMapper(UsersMapper.class);
			Map<String,Object> result = mapper.getUserByIdReturnMap(1);
			result.forEach((k,v)-> System.out.println(k+":"+v));
		}
	}

	@Test
	public  void test09() throws IOException {
		SqlSessionFactory sqlSessionFactory = this.getSqlSessionFactory();

		try(SqlSession openSession = sqlSessionFactory.openSession(true)) {
			UsersMapper mapper = openSession.getMapper(UsersMapper.class);
			Map<Integer,Users> result = mapper.selectUsersByUserNameReturnMap("user1");
			result.forEach((k,v)-> System.out.println(k+":"+v));
		}
	}


}
