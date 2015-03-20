package com.project.mechanic.model;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.mechanic.entity.ListItem;

public class DataBaseAdapter {

	// //////////////////////////////////// Fields ////////////////

	protected static final String TAG = "DataAdapter";

	private String[] ACL = { "ID", "UserId", "ListItemId" };
	private String[] AdvisorType = { "ID", "Name" };
	private String[] City = { "ID", "Name" };
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
		ListItem item = new ListItem(mCur.getInt(0), mCur.getString(1),
				mCur.getInt(2));
		return item;
	}
	// --------------------------------------------------------

}