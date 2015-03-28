package com.project.mechanic.model;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.mechanic.entity.City;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.entity.Province;
import com.project.mechanic.row_items.RowMain;
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
	private String[] ListItem = { "Id", "Name", "ListId" };
	private String[] Object = { "ID", "Name", "Phone", "Email", "Fax",
			"Description", "Image1", "Image2", "Image3", "Image4" };
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

	
	
public void insertCommenttoDb(String Comment){
		
		ContentValues cv=new ContentValues();
		cv.put("Description", Comment);
		mDb.insert(TableComment, null, cv);
	
		
	}

	
	
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
	// /////////////// ListItems ////////////////
	public ArrayList<ListItem> getListItemsById(int ListId) {

		ArrayList<ListItem> result = new ArrayList<ListItem>();
		ListItem item = null;
		Cursor mCur = mDb.query("ListItem", ListItem, "ListId=?",
				new String[] { String.valueOf(ListId) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToListItem(mCur);
			result.add(item);
		}

		return result;

	}

	private ListItem CursorToListItem(Cursor mCur) {

		ListItem item = new ListItem(mCur.getInt(0), mCur.getString(1),mCur.getInt(2));

	

		return item;
	}

	@SuppressWarnings("unused")
	private Province CursorToProvince(Cursor cursor) {
		Province tempProvince = new Province(cursor.getInt(0),
				cursor.getString(1));
		return tempProvince;


	}
	
	



	@SuppressWarnings("unused")
	private City CursorToCity(Cursor cursor) {
		City tempCity = new City(cursor.getInt(0), cursor.getString(1));
		return tempCity;
	}



	public ArrayList<RowMain> getAllProvinceName() {
		ArrayList<RowMain> result = new ArrayList<RowMain>();
		Cursor cursor = mDb.query(TableProvince, Province, null, null, null,
				null, null);
		RowMain tempProvince;
		while (cursor.moveToNext()) {
			tempProvince = new RowMain(cursor.getString(1));
			result.add(tempProvince);
		}
		return result;
	}

	public ArrayList<RowMain> getAllCityName() {
		ArrayList<RowMain> result = new ArrayList<RowMain>();
		Cursor cursor = mDb.query(TableCity, CityColumn, null, null, null,
				null, null);
		RowMain tempCity;
		while (cursor.moveToNext()) {
			tempCity = new RowMain(cursor.getString(1));
			result.add(tempCity);
		}

		return result;

	}

	public ArrayList<Froum> getAllFroum() {
		ArrayList<Froum> result = new ArrayList<Froum>();
		Cursor cursor = mDb.query(TableFroum, Froum, null, null, null, null,
				null);
		Froum tempFroum;
		while (cursor.moveToNext()) {
			tempFroum = new Froum(cursor.getInt(0), cursor.getInt(3),
					cursor.getString(2), cursor.getString(1));
			result.add(tempFroum);
		}

		return result;

	}

	public Integer province_count(String table) {

		Cursor cu = mDb.rawQuery("select * from " + table + " group by Name",
				null);
		int s = cu.getCount();
		return s;
	}

	public String province_display(String table, int row, int field) {

		Cursor cu = mDb.rawQuery("select * from " + table
				+ " group by Name order by ID", null);
		cu.moveToPosition(row);
		String s = cu.getString(field);
		return s;
	}

	public Integer City_count(String table) {

		Cursor cu = mDb.rawQuery("select * from " + table + " group by Name",
				null);
		int s = cu.getCount();
		return s;
	}

	public String City_display(String table, int row, int field) {

		Cursor cu = mDb.rawQuery("select * from " + table
				+ " group by Name order by ID", null);
		cu.moveToPosition(row);
		String s = cu.getString(field);
		return s;
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





public ArrayList<Users> getUserOfcomment(int froumId){
	ArrayList<Users> result = new ArrayList<Users>();
	Cursor cursor = mDb.rawQuery("Select "+ Users[0] +","+ Users[1] +","+Users[2] +"," + Users[3] + "  From Users inner join Comment on User.id=Comment.UserId where Comment.PaperId =" + froumId, null);
	while(cursor.moveToNext()){
		Users tempusers = new Users(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
		result.add(tempusers);
	}
	return result;
	
}


private Comment CursorToComment(Cursor cursor){
	Comment tempComment = new Comment(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),cursor.getString(3)  );
	return tempComment;
}

private Froum CursorToFroum(Cursor cursor){
	Froum tempFroum = new Froum(cursor.getInt(0), cursor.getInt(3), cursor.getString(2),cursor.getString(1)  );
	
	return tempFroum;
}


}

