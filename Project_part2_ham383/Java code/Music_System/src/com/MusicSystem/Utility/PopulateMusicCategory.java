package com.MusicSystem.Utility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.Connection.PopulateMusicCategoryDAO;
import com.MusicSystem.bean.MusicCategoryBean;

public class PopulateMusicCategory {
	public List<MusicCategoryBean> populateCategory() throws ConnectionFailureException, SQLException, IOException{
		return new PopulateMusicCategoryDAO().populateMusicCategory();
	}
}
