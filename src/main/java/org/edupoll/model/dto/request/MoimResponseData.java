package org.edupoll.model.dto.request;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.edupoll.model.entity.Moim;
import org.edupoll.model.entity.User;

public class MoimResponseData {
	String id;
	User user;
	String title;
	String cate;
	String description;
	Integer maxPerson;
	Integer currentPerson = 1;
	String targetDate;
	String joinTime;
	Integer duration;
	
	public MoimResponseData() {
		super();
	}
	
	public MoimResponseData(Moim t) {
		super();
		this.id = t.getId();
		this.user = t.getUser();
		this.title = t.getTitle();
		this.cate = t.getCate();
		this.description = t.getDescription();
		this.maxPerson = t.getMaxPerson();
		this.currentPerson = t.getCurrentPerson();
		this.targetDate = new SimpleDateFormat("yyyy-MM-dd").format(t.getTargetDate());
		long diff = (t.getTargetDate().getTime() - System.currentTimeMillis()) / (1000L * 60 * 60 * 24);
		this.joinTime = diff >= 0 ? (diff == 0 ? "당일" : (diff + 1) + "일 후") : "과거";
		this.duration = t.getDuration();
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

	public String getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

}
