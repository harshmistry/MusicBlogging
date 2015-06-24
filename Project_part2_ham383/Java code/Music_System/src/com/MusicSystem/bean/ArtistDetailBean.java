package com.MusicSystem.bean;

import java.sql.Date;

public class ArtistDetailBean {
	private String artistId;
	private String bandId;
	private String bandName;
	private Date bandCreateDate;
	private int musicCategoryId;
	private int musicSubCategoryId;
	private String musicCategoryName;
	private String musicSubCategoryName;
	
	public ArtistDetailBean() {
		// TODO Auto-generated constructor stub
	}

	public ArtistDetailBean(String artistId, String bandId, String bandName,
			Date bandCreateDate, int musicCategoryId, int musicSubCategoryId,
			String musicCategoryName, String musicSubCategoryName) {
		super();
		this.artistId = artistId;
		this.bandId = bandId;
		this.bandName = bandName;
		this.bandCreateDate = bandCreateDate;
		this.musicCategoryId = musicCategoryId;
		this.musicSubCategoryId = musicSubCategoryId;
		this.musicCategoryName = musicCategoryName;
		this.musicSubCategoryName = musicSubCategoryName;
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	public String getBandId() {
		return bandId;
	}

	public void setBandId(String bandId) {
		this.bandId = bandId;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public Date getBandCreateDate() {
		return bandCreateDate;
	}

	public void setBandCreateDate(Date bandCreateDate) {
		this.bandCreateDate = bandCreateDate;
	}

	public int getMusicCategoryId() {
		return musicCategoryId;
	}

	public void setMusicCategoryId(int musicCategoryId) {
		this.musicCategoryId = musicCategoryId;
	}

	public int getMusicSubCategoryId() {
		return musicSubCategoryId;
	}

	public void setMusicSubCategoryId(int musicSubCategoryId) {
		this.musicSubCategoryId = musicSubCategoryId;
	}

	public String getMusicCategoryName() {
		return musicCategoryName;
	}

	public void setMusicCategoryName(String musicCategoryName) {
		this.musicCategoryName = musicCategoryName;
	}

	public String getMusicSubCategoryName() {
		return musicSubCategoryName;
	}

	public void setMusicSubCategoryName(String musicSubCategoryName) {
		this.musicSubCategoryName = musicSubCategoryName;
	}
	
	
}
