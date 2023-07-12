package org.edupoll.model.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "moims")
public class Moim {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	@ManyToOne
	@JoinColumn(name = "managerId")
	User user;
	@NotBlank
	String title;
	@NotBlank
	String cate;
	@NotBlank
	String description;
	Integer maxPerson;
	Integer currentPerson = 1;
	@NotNull
	Date targetDate;
	Integer duration;

	@OneToMany(mappedBy = "moim", fetch = FetchType.LAZY)
	List<Reply> replies;
	
	@OneToMany(mappedBy = "moim", fetch = FetchType.LAZY)
	List<Attendance> attendances;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "attendances", joinColumns = @JoinColumn(name="moimId"), inverseJoinColumns = @JoinColumn(name="userId"))
	List<User> attendUsers;
	
	public List<Attendance> getAttendances() {
		return attendances;
	}
	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}
	public List<Reply> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getMaxPerson() {
		return maxPerson;
	}
	public void setMaxPerson(Integer maxPerson) {
		this.maxPerson = maxPerson;
	}
	public Integer getCurrentPerson() {
		return currentPerson;
	}
	public void setCurrentPerson(Integer currentPerson) {
		this.currentPerson = currentPerson;
	}
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	@Override
	public String toString() {
		return "Moim [id=" + id + ", title=" + title + ", cate=" + cate + ", description=" + description
				+ ", maxPerson=" + maxPerson + ", currentPerson=" + currentPerson + ", targetDate=" + targetDate
				+ ", duration=" + duration + "]";
	}
	
}
