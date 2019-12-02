package com.wh.mybatis.dto;

import org.apache.ibatis.type.Alias;

@Alias("u")
public class Users {
	private Integer id;
	private String userName;
	private String passWord;
	private String userSex;
	private String nickName;
	private Department department;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Users() {
	}

	public Users(Integer id,String userName, String passWord, String userSex, String nickName) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.userSex = userSex;
		this.nickName = nickName;
	}

	public Users(String userName, String passWord, String userSex, String nickName) {
		this.userName = userName;
		this.passWord = passWord;
		this.userSex = userSex;
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "Users [userName=" + userName + ", passWord=" + passWord + ", userSex=" + userSex + ", nickName="
				+ nickName + "]";
	}
	
	
}
