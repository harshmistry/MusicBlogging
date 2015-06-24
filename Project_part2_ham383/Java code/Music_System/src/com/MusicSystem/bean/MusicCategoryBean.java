package com.MusicSystem.bean;

import java.util.ArrayList;

public class MusicCategoryBean {
	private int musicCategoryID;
	private String categoryName;
	private String categoryDescription;
	private ArrayList<MusicSubCategoryBean> subCategoryBeanList;
	
	public MusicCategoryBean() {
	}

	public MusicCategoryBean(int musicCategoryID, String categoryName,
			String categoryDescription, ArrayList<MusicSubCategoryBean> subCategoryBean) {
		super();
		this.musicCategoryID = musicCategoryID;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.subCategoryBeanList = subCategoryBean;
	}

	public int getMusicCategoryID() {
		return musicCategoryID;
	}

	public void setMusicCategoryID(int musicCategoryID) {
		this.musicCategoryID = musicCategoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public ArrayList<MusicSubCategoryBean> getSubCategoryBean() {
		return subCategoryBeanList;
	}

	public void setSubCategoryBean(ArrayList<MusicSubCategoryBean> subCategoryBean) {
		this.subCategoryBeanList = subCategoryBean;
	}
		
}
