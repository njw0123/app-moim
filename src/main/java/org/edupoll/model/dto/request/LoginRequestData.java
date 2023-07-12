package org.edupoll.model.dto.request;

public class LoginRequestData {
	String id;
	String pass;
	
	public LoginRequestData() {
		super();
	}
	public LoginRequestData(String id, String pass) {
		super();
		this.id = id;
		this.pass = pass;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "LoginRequestData [id=" + id + ", pass=" + pass + "]";
	}
	
}
