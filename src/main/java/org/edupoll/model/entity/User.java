package org.edupoll.model.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Pattern(regexp = "[a-z0-9]+")
	String id;
	String pass;
	String nick;
	Date joinDate = new Date();
	String authority;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userDetailIdx") // User Entity 의 필드
	UserDetail userDetail; // 이 칼럼을 찾는 객체는 UserDetail (id를 기준으로 찾음)	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	List<Moim> moims;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "attendances", joinColumns = @JoinColumn(name="userId"), inverseJoinColumns = @JoinColumn(name="moimId"))
	List<Moim> attendMoims;
	
	
	

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public List<Moim> getMoims() {
		return moims;
	}

	public void setMoims(List<Moim> moims) {
		this.moims = moims;
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

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
