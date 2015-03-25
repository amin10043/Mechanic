package com.project.mechanic.model;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.mechanic.entity.*;

public class DataBaseAdapter {

	// //////////////////////////////////// Fields ////////////////

	protected static final String TAG = "DataAdapter";
	
	private String TableCity = "City";
	private String TableFroum = "Froum";

	private String[] ACL = { "ID", "UserId", "ListItemId" };
	private String[] AdvisorType = { "ID", "Name" };
	private String[] CityColumn = { "ID", "Name" };
	private String[] Comment = { "ID", "UserId", "paperId", "Description" };
	private String[] Executertype = { "ID", "Name" };
	private String[] Favorite = { "ID", "ObjectId", "UserId" };
	private String[] Froum = { "ID", "Title", "Description", "UserId" };
	private String[] Like = { "ID", "UserId", "PaperId" };
	private String[] List = { "ID", "Name", "ParentId" };
	private String[] ListItem = { "ID", "Name", "ListId" };
	private String[] Object = { "ID", "Name", "Phone", "Email", "Fax", "Description", "Image1", "Image2", "Image3", "Image4" };
	private String[] ObjectInCity = { "ID", "ObjectId", "CityId" };
	private String[] ObjectInProvince = { "ID", "ObjectId", "ProvinceId" };
	private String[] ObjectType = { "ID", "Name" };
	private String[] Paper = { "ID", "Title", "Context" };
	private String[] PaperType = { "ID", "Name" };
	private String[] Province = { "ID", "Name" };
	private String[] Users = { "ID", "Name", "Email", "Password" };
	private String[] WorkmanType = { "ID", "Name" };
	

	private final Context mContext;
	private SQLiteDatabase mDb;
	private DataBaseHelper mDbHelper;

	// //////////////////////////////////// Constructor

	public DataBaseAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
	}

	public DataBaseAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public DataBaseAdapter open() {
		try {

			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (Exception mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	// --------------------------------------------------------
public ArrayList<City> getAllCity(){
	ArrayList<City> result = new ArrayList<City>();
	Cursor cursor = mDb.query(TableCity, CityColumn, null, null, null, null, null);
	City tempCity;
	while(cursor.moveToNext()){
		tempCity = new City(cursor.getInt(0), cursor.getString(1));
		result.add(tempCity);
	}
	
	
	return result;
	
}

public ArrayList<Froum> getAllFroum(){
	ArrayList<Froum> result = new ArrayList<Froum>();
	Cursor cursor = mDb.query(TableFroum, Froum , null, null, null, null, null);
	Froum tempFroum;
	while(cursor.moveToNext()){
		tempFroum = new Froum(cursor.getInt(0), cursor.getInt(3), cursor.getString(2),cursor.getString(1)  );
		result.add(tempFroum);
	}
	
	
	return result;
	
}


}