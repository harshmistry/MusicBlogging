package com.MusicSystem.bean;

import java.sql.Date;

public class ConcertDetailsBean {
	private int concert_id;
	private String artist_id;
	private String band_id;
	private String concert_venue_city;
	private String concert_venue_state;
	private int concert_price;
	private Date concert_date;
	private int total_tickets;
	private String concert_time;
	private int music_category_id;
	private int music_subcategory_id;
	private String concert_name;
	private String music_category_name;
	private String music_subcategory_name;
	
	public ConcertDetailsBean() {
		// TODO Auto-generated constructor stub
	}

	public ConcertDetailsBean(int concert_id, String artist_id, String band_id,
			String concert_venue_city, String concert_venue_state,
			int concert_price, Date concert_date, int total_tickets,
			String concert_time, int music_category_id,
			int music_subcategory_id, String concert_name) {
		super();
		this.concert_id = concert_id;
		this.artist_id = artist_id;
		this.band_id = band_id;
		this.concert_venue_city = concert_venue_city;
		this.concert_venue_state = concert_venue_state;
		this.concert_price = concert_price;
		this.concert_date = concert_date;
		this.total_tickets = total_tickets;
		this.concert_time = concert_time;
		this.music_category_id = music_category_id;
		this.music_subcategory_id = music_subcategory_id;
		this.concert_name = concert_name;
	}

	public int getConcert_id() {
		return concert_id;
	}

	public void setConcert_id(int concert_id) {
		this.concert_id = concert_id;
	}

	public String getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(String artist_id) {
		this.artist_id = artist_id;
	}

	public String getBand_id() {
		return band_id;
	}

	public void setBand_id(String band_id) {
		this.band_id = band_id;
	}

	public String getConcert_venue_city() {
		return concert_venue_city;
	}

	public void setConcert_venue_city(String concert_venue_city) {
		this.concert_venue_city = concert_venue_city;
	}

	public String getConcert_venue_state() {
		return concert_venue_state;
	}

	public void setConcert_venue_state(String concert_venue_state) {
		this.concert_venue_state = concert_venue_state;
	}

	public int getConcert_price() {
		return concert_price;
	}

	public void setConcert_price(int concert_price) {
		this.concert_price = concert_price;
	}

	public Date getConcert_date() {
		return concert_date;
	}

	public void setConcert_date(Date concert_date) {
		this.concert_date = concert_date;
	}

	public int getTotal_tickets() {
		return total_tickets;
	}

	public void setTotal_tickets(int total_tickets) {
		this.total_tickets = total_tickets;
	}

	public String getConcert_time() {
		return concert_time;
	}

	public void setConcert_time(String concert_time) {
		this.concert_time = concert_time;
	}

	public int getMusic_category_id() {
		return music_category_id;
	}

	public void setMusic_category_id(int music_category_id) {
		this.music_category_id = music_category_id;
	}

	public int getMusic_subcategory_id() {
		return music_subcategory_id;
	}

	public void setMusic_subcategory_id(int music_subcategory_id) {
		this.music_subcategory_id = music_subcategory_id;
	}

	public String getConcert_name() {
		return concert_name;
	}

	public void setConcert_name(String concert_name) {
		this.concert_name = concert_name;
	}

	public String getMusic_category_name() {
		return music_category_name;
	}

	public void setMusic_category_name(String music_category_name) {
		this.music_category_name = music_category_name;
	}

	public String getMusic_subcategory_name() {
		return music_subcategory_name;
	}

	public void setMusic_subcategory_name(String music_subcategory_name) {
		this.music_subcategory_name = music_subcategory_name;
	}
	
	
}
