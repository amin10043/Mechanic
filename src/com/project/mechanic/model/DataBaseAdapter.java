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

	protected static final String TAG = "DataAdapter";
	
	private String TableCity = "City";
	private String TableACL = "ACL";
	private String TableAdvisorType = "AdvisorType";
	private String TableCityColumn = "CityColumn";
	private String TableComment = "Comment";
	private String TableExecutertype = "Executertype";
	private String TableFavorite = "Favorite";
	private String TableFroum = "Froum";
	private String TableLike = "Like";
	private String TableList = "List";
	private String TableListItem = "ListItem";
	private String TableObject = "Object";
	private String TableObjectInCity = "ObjectInCity";
	private String TableObjectInProvince = "ObjectInProvince";
	private String TableObjectType = "ObjectType";
	private String TablePaper = "Paper";
	private String TablePaperType = "PaperType";
	private String TableProvince = "Province";
	private String TableUsers = "Users";
	private String TableWorkmanType = "WorkmanType";


	
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
	
	public ArrayList<Province> getAllProvince(){
		ArrayList<Province> result = new ArrayList<Province>();
		Cursor cursor = mDb.query(TableProvince, Province, null, null, null, null, null);
		Province tempProvince;
		while(cursor.moveToNext()){
			tempProvince = new Province(cursor.getInt(0), cursor.getString(1));
			result.add(tempProvince);
		}
		
		
		return result;
		
	}
	
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
		
		result.add(CursorToFroum( cursor));
	}
	
	
	return result;
	
}


/*public String getUseridFroum(){
	ArrayList<Froum> result = new ArrayList<Froum>();
	String[] s = new String[1];
	s[0] = "UserId";
	Cursor cursor = mDb.query(TableFroum,s , null,null , null, null, null);
	Froum tempFroum;
	if(cursor.moveToNext()){
		tempFroum = new Froum(cursor.getInt(0), cursor.getInt(3), cursor.getString(2),cursor.getString(1)  );
		result.add(tempFroum);
	}
	
	
	return result;
	
}*/

public ArrayList<Comment> getAllComment(){
	ArrayList<Comment> result = new ArrayList<Comment>();
	Cursor cursor = mDb.query(TableComment, Comment , null, null, null, null, null);
	Comment tempComment;
	while(cursor.moveToNext()){
		result.add(CursorToComment(cursor));
	}
	
	
	return result;
	
}

public ArrayList<Integer>  getUSeridComment(){
	ArrayList<Integer> result = new ArrayList<Integer>();
	String[] s = new String[1];
	s[0]="UserId";
	Cursor cursor = mDb.query(TableComment, s , null, null, null, null, null);
	
	while(cursor.moveToNext()){
		Integer x = cursor.getInt(0);
		result.add(x);
	}
	
	
	return result;
	
}





/*public String getUsernameOfcomment(String UserId){
	String result = new String();
	String[] s = new String[1];
	s[0]="Name";
	Cursor cursor = mDb.query(TableUsers,s,  selection, selectionArgs, groupBy, having, orderBy)
	Comment tempComment;
	while(cursor.moveToNext()){
		result.add(CursorToComment(cursor));
	}
	
	
	return result;
	
}*/


private Comment CursorToComment(Cursor cursor){
	Comment tempComment = new Comment(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),cursor.getString(3)  );
	return tempComment;
}

private Froum CursorToFroum(Cursor cursor){
	Froum tempFroum = new Froum(cursor.getInt(0), cursor.getInt(3), cursor.getString(2),cursor.getString(1)  );
	
	return tempFroum;
}


}
