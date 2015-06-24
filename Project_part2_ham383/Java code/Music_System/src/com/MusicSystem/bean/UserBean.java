package com.MusicSystem.bean;

import java.sql.Date;

public class UserBean {
	private String userid;
	private String firstName;
	private String lastName;
	private double phoneNo;
	private Date dob;
	private String city;
	private String state;
	private int isArtist;
	private int trustScore;
	private String artistID;
	private String password;
	
	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	
	public UserBean(String userid, String firstName, String lastName,
			double phoneNo, Date dob, String city, String state, int isArtist,
			int trustScore,String artistID,String password) {
		super();
		this.userid = userid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.dob = dob;
		this.city = city;
		this.state = state;
		this.isArtist = isArtist;
		this.trustScore = trustScore;
		this.artistID = artistID;
		this.password = password;
	}


	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(double phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getIsArtist() {
		return isArtist;
	}
	public void setIsArtist(int isArtist) {
		this.isArtist = isArtist;
	}
	public int getTrustScore() {
		return trustScore;
	}
	public void setTrustScore(int trustScore) {
		this.trustScore = trustScore;
	}
	
	public String getArtistID(){
		return artistID;
	}
	public void setArtistID(String artistID){
		this.artistID = artistID;
	}
	
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
}
