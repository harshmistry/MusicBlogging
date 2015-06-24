package com.MusicSystem.bean;

import java.sql.Date;

public class AttendConcertBean {
	private int concertID;
	private String userId;
	private String concertReview;
	private String concertRating;
	private Date reviewDateTime;
	
	public AttendConcertBean() {
		// TODO Auto-generated constructor stub
	}

	public AttendConcertBean(int concertID, String userId,
			String concertReview, String concertRating, Date reviewDateTime) {
		super();
		this.concertID = concertID;
		this.userId = userId;
		this.concertReview = concertReview;
		this.concertRating = concertRating;
		this.reviewDateTime = reviewDateTime;
	}

	public int getConcertID() {
		return concertID;
	}

	public void setConcertID(int concertID) {
		this.concertID = concertID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getConcertReview() {
		return concertReview;
	}

	public void setConcertReview(String concertReview) {
		this.concertReview = concertReview;
	}

	public String getConcertRating() {
		return concertRating;
	}

	public void setConcertRating(String concertRating) {
		this.concertRating = concertRating;
	}

	public Date getReviewDateTime() {
		return reviewDateTime;
	}

	public void setReviewDateTime(Date reviewDateTime) {
		this.reviewDateTime = reviewDateTime;
	}
	
}
