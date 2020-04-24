package jp.co.internous.ecsite.model.dto;

import jp.co.internous.ecsite.model.entity.User;

public class LoginDto {
	
	private long id;
	private String userName;
	private String password;
	private String fullName;
	
//LoginDto は、コンストラクタを３つ持つ。以下の3パターンでインスタンス化できるようにするため。
	
//①インスタンス化の際に初期設定せず、あとから一つずつSetterを使ってデータをセット
	public LoginDto() {}
	
//②Userエンティティをまとめて受け取りデータをセット
	public LoginDto(User user) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.fullName = user.getFullName();
		}
	
//③引数を分けて受け取り、データをセット
	public LoginDto(long id, String userName, String password, String fullName) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
