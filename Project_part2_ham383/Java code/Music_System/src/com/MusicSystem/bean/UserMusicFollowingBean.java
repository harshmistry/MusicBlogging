package com.MusicSystem.bean;

public class UserMusicFollowingBean {
	private String userID;
	private int musicCatID;
	private int subCategoryID;
	
	public UserMusicFollowingBean() {
		// TODO Auto-generated constructor stub
	}

	public UserMusicFollowingBean(String userID, int musicCatID,
			int subCategoryID) {
		super();
		this.userID = userID;
		this.musicCatID = musicCatID;
		this.subCategoryID = subCategoryID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getMusicCatID() {
		return musicCatID;
	}

	public void setMusicCatID(int musicCatID) {
		this.musicCatID = musicCatID;
	}

	public int getSubCategoryID() {
		return subCategoryID;
	}

	public void setSubCategoryID(int subCategoryID) {
		this.subCategoryID = subCategoryID;
	}
		
}
