package com.MusicSystem.bean;

public class MusicSubCategoryBean {
	private int subCategoryID;
	private int categoryID;
	private String subCategoryName;
	private String subCategoryDescription;
	
	public MusicSubCategoryBean() {
		
	}
	
	public MusicSubCategoryBean(int subCategoryID, int categoryID,
			String subCategoryName, String subCategoryDescription) {
		super();
		this.subCategoryID = subCategoryID;
		this.categoryID = categoryID;
		this.subCategoryName = subCategoryName;
		this.subCategoryDescription = subCategoryDescription;
	}
	
	public int getSubCategoryID() {
		return subCategoryID;
	}


	public void setSubCategoryID(int subCategoryID) {
		this.subCategoryID = subCategoryID;
	}


	public int getCategoryID() {
		return categoryID;
	}


	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}


	public String getSubCategoryName() {
		return subCategoryName;
	}


	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}


	public String getSubCategoryDescription() {
		return subCategoryDescription;
	}


	public void setSubCategoryDescription(String subCategoryDescription) {
		this.subCategoryDescription = subCategoryDescription;
	}
	
	
}
