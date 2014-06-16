package com.trink.bean;

public class User {
	private Integer id;
	private String account;
	private String password;
	private String nickname;
	private String role;
	private Integer friends;
	private Integer images;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFriends() {
		return friends;
	}
	public void setFriends(Integer friends) {
		this.friends = friends;
	}
	public Integer getImages() {
		return images;
	}
	public void setImages(Integer images) {
		this.images = images;
	}
}
