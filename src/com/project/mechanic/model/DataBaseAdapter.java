package com.project.mechanic.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.project.mechanic.entity.AdvisorType;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.Executertype;
import com.project.mechanic.entity.Favorite;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.LikeInComment;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.entity.News;
import com.project.mechanic.entity.NewsPaper;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Province;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.TicketType;
import com.project.mechanic.entity.Users;
import com.project.mechanic.row_items.RowMain;

public class DataBaseAdapter {

	protected static final String TAG = "DataAdapter";

	private static final String id = null;

	private String TableCity = "City";
	private String TableACL = "ACL";
	private String TableAdvisorType = "AdvisorType";
	private String TableAnad = "Anad";
	private String TableCityColumn = "CityColumn";
	private String TableComment = "Comment";
	private String TableExecutertype = "Executertype";
	private String TableFavorite = "Favorite";
	private String TableFroum = "Froum";
	private String TableLike = "Like";
	private String TableLikeInObject = "LikeInObject";
	private String TableLikeInFroum = "LikeInFroum";
	private String TableLikeInPaper = "LikeInPaper";
	private String TableList = "List";
	private String TableListItem = "ListItem";
	private String TableNews = "News";
	private String TableNewsPaper = "NewsPaper";
	private String TableObject = "Object";
	private String TableObjectInCity = "ObjectInCity";
	private String TableObjectInProvince = "ObjectInProvince";
	private String TableObjectType = "ObjectType";
	private String TablePaper = "Paper";
	private String TablePaperType = "PaperType";
	private String TableProvince = "Province";
	private String TableTicket = "Ticket";
	private String TableTicketType = "TicketType";
	private String TableUsers = "Users";
	private String TableWorkmanType = "WorkmanType";
	private String TableCommentInObject = "CommentInObject";
	private String TableCommentInFroum = "CommentInFroum";
	private String TableLikeInComment = "LikeInComment";

	private String TableCommentInPaper = "CmtInPaper";

	private String TableObjectBrandType = "ObjectBrandType";

	private String[] ACL = { "ID", "UserId", "ListItemId" };
	private String[] AdvisorType = { "ID", "Name" };
	private String[] Anad = { "Id", "Image", "ObjectId", "Date", "TypeId",
			"ProvinceId", "Seen", "Submit" };
	private String[] CityColumn = { "ID", "Name", "ProvinceId", "Count" };

	private String[] Comment = { "ID", "UserId", "paperId", "Description" };
	private String[] CommentInObject = { "Id", "Desk", "ObjectId", "UserId",
			"Date", "CommentId", "Seen" };
	private String[] CommentInFroum = { "ID", "Desk", "FroumId", "UserId",

	"Date", "CommentId", "NumOfDislike", "NumOfLike", "Seen" };

	private String[] CommentInPaper = { "Id", "Desk", "PaperId", "UserId",
			"Date", "CommentId", "Seen" };

	private String[] Executertype = { "ID", "Name" };
	private String[] Favorite = { "ID", "ObjectId", "UserId", "IdTicket" };
	private String[] Froum = { "ID", "UserId", "Title", "Description", "Seen",
			"ServerDate", "Submit", "Date" };
	private String[] Like = { "ID", "UserId", "PaperId" };
	private String[] LikeInObject = { "Id", "UserId", "PaperId", "Date",
			"CommentId", "Seen" };
	private String[] LikeInFroum = { "Id", "UserId", "FroumId", "Date",
			"CommentId" };
	private String[] LikeInComment = { "ID", "CommentId", "UserId", "IsLike" };
	private String[] LikeInPaper = { "Id", "UserId", "PaperId", "Date",
			"CommentId", "Seen" };
	private String[] List = { "ID", "Name", "ParentId" };
	private String[] ListItem = { "Id", "Name", "ListId" };
	private String[] News = { "ID", "Title", "Description", "ServerDate",
			"Submit" };
	private String[] Object = { "ID", "Name", "Phone", "Email", "Fax",
			"Description", "Image1", "Image2", "Image3", "Image4", "Pdf1",
			"Pdf2", "Pdf3", "Pdf4", "Address", "CellPhone", "ObjectTypeId",
			"ObjectBrandTypeId", "Facebook", "Instagram", "LinkedIn", "Google",
			"Site", "Twitter", "ParentId", "rate", "Seen", "ServerDate",
			"Submit", "MainObjectId" };
	private String[] ObjectInCity = { "ID", "ObjectId", "CityId" };
	private String[] ObjectInProvince = { "ID", "ObjectId", "ProvinceId" };
	private String[] ObjectType = { "ID", "Name" };
	private String[] Paper = { "ID", "Title", "Context", "Seen", "ServerDate",
			"Submit" };
	private String[] PaperType = { "ID", "Name" };
	private String[] Province = { "ID", "Name", "Count" };

	private String[] Ticket = { "Id", "Title", "Desc", "UserId", "Image",
			"date", "TypeId", "Name", "Email", "Mobile", "Phone", "Fax",
			"ProvinceId", "UName", "UEmail", "UPhonnumber", "UFax", "UAdress",
			"UImage", "UMobile", "Seen", "Submit" };

	private String[] TicketType = { "ID", "desc" };

	private String[] Users = { "ID", "Name", "Email", "Password",
			"Phonenumber", "Mobailenumber", "Faxnumber", "Address", "Image",
			"ServiceId", "ServerDate", "Date", "Submit", "Admin" };

	private String[] WorkmanType = { "ID", "Name" };
	private String[] NewsPaper = { "ID", "Name", "TypeId", "Url", "ServerDate" };
	private String[] ObjectBrandType = { "ID", "Description" };

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

	public void inserUserToDb(String name, String email, String password,
			String phonenumber, String mobailenumber, String faxnumber,
			String address, byte[] image, int serviceid, String date) {

		ContentValues uc = new ContentValues();

		uc.put("Name", name);
		uc.put("Email", email);
		uc.put("Password", password);
		uc.put("Phonenumber", phonenumber);

		uc.put("Mobailenumber", mobailenumber);
		uc.put("Faxnumber", faxnumber);
		uc.put("Address", address);
		uc.put("Image", image);
		uc.put("ServiceId", serviceid);

		uc.put("Date", date);
		long res = mDb.insert(TableUsers, null, uc);
		long res2 = res;

	}

	public void insertAnadToDb(byte[] image, int objectId, String date,
			int tyoeId, int provinceId, int seen) {

		ContentValues uc = new ContentValues();

		uc.put("Image", image);
		uc.put("ObjectId", objectId);
		uc.put("Date", date);
		uc.put("TypeId", tyoeId);
		uc.put("ProvinceId", provinceId);
		uc.put("Seen", seen);
		long res = mDb.insert(TableAnad, null, uc);
		long res2 = res;

	}

	public void inserUsernonpicToDb(String name, String email, String password,
			String phonenumber, String mobailenumber, String faxnumber,
			String address, int serviceid, String date) {

		ContentValues uc = new ContentValues();

		uc.put("Name", name);
		uc.put("Email", email);
		uc.put("Password", password);
		uc.put("Phonenumber", phonenumber);

		uc.put("Mobailenumber", mobailenumber);
		uc.put("Faxnumber", faxnumber);
		uc.put("Address", address);

		uc.put("ServiceId", serviceid);

		uc.put("Date", date);
		long res = mDb.insert(TableUsers, null, uc);
		long res2 = res;

	}

	// ///////////
	// public Cursor getAllUsers() {
	// return mDb.query(TableUsers, new String[] { KEY_ROWID, KEY_NAME,
	// KEY_EMAIL }, null, null, null, null, null);
	// }
	//
	// public Cursor getUsers(long ID) throws SQLException {
	// Cursor mCursor = mDb.query(true, TableUsers, new String[] {
	// KEY_ROWID, KEY_NAME, KEY_EMAIL }, id + "=" + ID,
	// null, null, null, null, null);
	// if (mCursor != null) {
	// mCursor.moveToFirst();
	// }
	// return mCursor;
	// }
	// ///////////////

	public void UpdateAdminAllUser(int admin) {

		ContentValues uc = new ContentValues();
		// uc.put("Name", name);
		// uc.put("Email", email);
		// uc.put("Password", password);
		// uc.put("Phonenumber", phonenumber);
		//
		// uc.put("Mobailenumber", mobailenumber);
		// uc.put("Faxnumber", faxnumber);
		uc.put("Admin", admin);

		// uc.put("ServiceId", serviceid);
		mDb.update(TableUsers, uc, null, null);

	}

	public void UpdateAdminUserToDb(int id, int admin) {

		ContentValues uc = new ContentValues();
		// uc.put("Name", name);
		// uc.put("Email", email);
		// uc.put("Password", password);
		// uc.put("Phonenumber", phonenumber);
		//
		// uc.put("Mobailenumber", mobailenumber);
		// uc.put("Faxnumber", faxnumber);
		uc.put("Admin", admin);

		// uc.put("ServiceId", serviceid);
		mDb.update(TableUsers, uc, "ID=" + id, null);
	}

	public void UpdateUserToDb(int id, String phonenumber,
			String mobailenumber, String faxnumber, String address) {

		ContentValues uc = new ContentValues();
		// uc.put("Name", name);
		// uc.put("Email", email);
		// uc.put("Password", password);
		uc.put("Phonenumber", phonenumber);

		uc.put("Mobailenumber", mobailenumber);
		uc.put("Faxnumber", faxnumber);
		uc.put("Address", address);

		// uc.put("ServiceId", serviceid);
		mDb.update(TableUsers, uc, "ID=" + id, null);
	}

	public void UpdateTicketToDb(int id, String title, String desc,
			byte[] image, String name, String date, String email, String phone,
			String fax, String mobile) {

		ContentValues uc = new ContentValues();
		// uc.put("Name", name);
		// uc.put("Email", email);
		// uc.put("Password", password);
		uc.put("Title", title);
		uc.put("Desc", desc);
		uc.put("Image", image);
		uc.put("UName", name);
		uc.put("UEmail", email);
		uc.put("UPhonnumber", phone);
		uc.put("UFax", fax);
		uc.put("UMobile", mobile);
		uc.put("Date", date);

		// uc.put("ServiceId", serviceid);
		mDb.update(TableTicket, uc, "Id=" + id, null);
	}

	// /////////////////

	public void UpdateAllUserToDb(int id, String email, String password,
			String phonenumber, String mobailenumber, String faxnumber,
			String address, byte[] image) {

		ContentValues uc = new ContentValues();
		// uc.put("Name", name);
		uc.put("Email", email);
		uc.put("Password", password);
		uc.put("Phonenumber", phonenumber);

		uc.put("Mobailenumber", mobailenumber);
		uc.put("Faxnumber", faxnumber);
		uc.put("Address", address);
		uc.put("Image", image);
		// uc.put("ServiceId", serviceid);
		mDb.update(TableUsers, uc, "ID=" + id, null);
	}

	// ///////////////////////////////////

	public void insertLikeInObjectToDb(int UserId, int PaperId, String Date,
			int CommentId) {

		ContentValues uc = new ContentValues();

		uc.put("UserId", UserId);
		uc.put("PaperId", PaperId);
		uc.put("CommentId", CommentId);
		uc.put("Date", Date);

		long res = mDb.insert(TableLikeInObject, null, uc);
		long res2 = res;

	}

	public void insertLikeInFroumToDb(int UserId, int FroumId, String Date,
			int CommentId) {
		if (!isUserLikedFroum(UserId, FroumId)) {
			ContentValues uc = new ContentValues();

			uc.put("UserId", UserId);
			uc.put("FroumId", FroumId);
			uc.put("CommentId", CommentId);
			uc.put("Date", Date);
			uc.put("Seen", 1);

			long res = mDb.insert(TableLikeInFroum, null, uc);
			long res2 = res;
		}

	}

	public boolean isUserLikedFroum(int userId, int FroumID) {

		Cursor curs = mDb.rawQuery("SELECT COUNT(*) AS NUM FROM "
				+ TableLikeInFroum + " WHERE UserId= " + String.valueOf(userId)
				+ " AND FroumId=" + String.valueOf(FroumID), null);
		if (curs.moveToNext()) {
			int number = curs.getInt(0);
			if (number > 0)
				return true;
		}
		return false;
	}

	public void insertLikeInPaperToDb(int UserId, int PaperId, String Date) {

		if (!isUserLikedPaper(UserId, PaperId)) {
			ContentValues uc = new ContentValues();

			uc.put("UserId", UserId);
			uc.put("PaperId", PaperId);
			uc.put("Date", Date);

			long res = mDb.insert(TableLikeInPaper, null, uc);
			long res2 = res;
		}

	}

	public boolean isUserLikedPaper(int userId, int paperId) {

		Cursor curs = mDb.rawQuery("SELECT COUNT(*) AS NUM FROM "
				+ TableLikeInPaper + " WHERE UserId= " + String.valueOf(userId)
				+ " AND PaperId=" + String.valueOf(paperId), null);
		if (curs.moveToNext()) {
			int number = curs.getInt(0);
			if (number > 0)
				return true;
		}
		return false;
	}

	public void insertCommenttoDb(int userId, int paperId, String description) {

		ContentValues cv = new ContentValues();

		cv.put("Description", description);
		cv.put("UserId", userId);

		cv.put("paperId", paperId);
		mDb.insert(TableComment, null, cv);
	}

	public void insertCommentInFroumtoDb(String description, int Froumid,
			int userid, String datetime, int commentid, String numofDisLike,
			String numoflike) {

		ContentValues cv = new ContentValues();
		cv.put("Desk", description);
		cv.put("UserId", userid);
		cv.put("FroumID", Froumid);
		cv.put("Date", datetime);
		cv.put("CommentId", commentid);
		cv.put("NumOfDislike", numofDisLike);
		cv.put("NumOfLike", numoflike);
		mDb.insert(TableCommentInFroum, null, cv);
	}

	public void insertCommentInPapertoDb(String description, int Paperid,
			int userid, String datetime) {

		ContentValues cv = new ContentValues();
		cv.put("Desk", description);
		cv.put("UserId", userid);
		cv.put("PaperID", Paperid);
		cv.put("Date", datetime);
		mDb.insert(TableCommentInPaper, null, cv);
	}

	public void insertCommentObjecttoDb(String description, int Objectid,
			int userid, String datetime, int commentid) {

		ContentValues cv = new ContentValues();

		cv.put("Desk", description);
		cv.put("UserId", userid);
		cv.put("ObjectID", Objectid);
		cv.put("Date", datetime);
		cv.put("CommentId", commentid);
		mDb.insert(TableCommentInObject, null, cv);
	}

	public void insertFroumtitletoDb(String Title, String description,
			int userId) {

		ContentValues cv = new ContentValues();
		cv.put("Title", Title);
		cv.put("Description", description);
		cv.put("UserId", userId);

		mDb.insert(TableFroum, null, cv);

	}

	public void insertTickettoDb(String Title, String desc, int userId,

	byte[] bytes, String date, int typeId, int name, int email, int mobile,
			int phone, int fax, int provinceId, String uname, String uemail,
			String uphonnumber, String ufax, String uadress, byte[] uimage,
			String umobile) {

		ContentValues cv = new ContentValues();
		cv.put("Title", Title);
		cv.put("Desc", desc);
		cv.put("UserId", userId);
		cv.put("Image", bytes);
		cv.put("Date", date);
		cv.put("TypeId", typeId);
		cv.put("Name", name);
		cv.put("Email", email);
		cv.put("Mobile", mobile);
		cv.put("Phone", phone);
		cv.put("Fax", fax);
		cv.put("ProvinceId", provinceId);
		cv.put("UName", uname);
		cv.put("UEmail", uemail);
		cv.put("UPhonnumber", uphonnumber);
		cv.put("UFax", ufax);
		cv.put("UAdress", uadress);
		cv.put("UImage", uimage);
		cv.put("UMobile", umobile);
		mDb.insert(TableTicket, null, cv);

	}

	public void insertTickettoDbemptyImage(String Title, String desc,
			int userId, String date, int typeId, int name, int email,
			int mobile, int phone, int fax, int provinceId, String uname,
			String uemail, String uphonnumber, String ufax, String uadress,
			String umobile) {

		ContentValues cv = new ContentValues();

		cv.put("Title", Title);
		cv.put("Desc", desc);
		cv.put("UserId", userId);
		cv.put("Date", date);
		cv.put("TypeId", typeId);
		cv.put("Name", name);
		cv.put("Email", email);
		cv.put("Mobile", mobile);
		cv.put("Phone", phone);
		cv.put("Fax", fax);
		cv.put("ProvinceId", provinceId);
		cv.put("UName", uname);
		cv.put("UEmail", uemail);
		cv.put("UPhonnumber", uphonnumber);
		cv.put("UFax", ufax);
		cv.put("UAdress", uadress);
		cv.put("UMobile", umobile);

		mDb.insert(TableTicket, null, cv);

	}

	public void insertFavoritetoDb(int ObjectId, int userId, int IdTicket) {

		ContentValues cv = new ContentValues();

		cv.put("ObjectId", ObjectId);
		cv.put("UserId", userId);
		cv.put("IdTicket", IdTicket);

		mDb.insert(TableFavorite, null, cv);

	}

	public void insertPapertitletoDb(String Title, String Context) {

		ContentValues cv = new ContentValues();
		cv.put("Title", Title);
		cv.put("Context", Context);

		mDb.insert(TablePaper, null, cv);

	}

	public Integer Tablecommentcount() {
		Cursor cu = mDb.query(TableComment, null, null, null, null, null, null);
		int s = cu.getCount();
		return s;
	}

	public ArrayList<Province> getAllProvince() {

		ArrayList<Province> result = new ArrayList<Province>();
		Cursor cursor = mDb.query(TableProvince, Province, null, null, null,
				null, null);
		Province tempProvince;
		while (cursor.moveToNext()) {
			tempProvince = new Province(cursor.getInt(0), cursor.getString(1),
					cursor.getInt(2));
			result.add(tempProvince);
		}

		Collections.sort(result);
		return result;

	}

	public ArrayList<City> getAllCity() {
		ArrayList<City> result = new ArrayList<City>();
		Cursor cursor = mDb.query(TableCity, CityColumn, null, null, null,
				null, null);
		City tempCity;
		while (cursor.moveToNext()) {
			tempCity = new City(cursor.getInt(0), cursor.getString(1),
					cursor.getInt(2), cursor.getInt(3));
			result.add(tempCity);
		}
		return result;
	}

	public ArrayList<Object> getAllObject() {
		ArrayList<Object> result = new ArrayList<Object>();
		Cursor cursor = mDb.query(TableObject, Object, null, null, null, null,
				null);
		Object tempObject;
		while (cursor.moveToNext()) {
			tempObject = new Object(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5), null, null, null,
					null, cursor.getString(6), cursor.getString(7),
					cursor.getString(8), cursor.getString(9),
					cursor.getString(10), cursor.getString(11),
					cursor.getInt(12), cursor.getInt(13), cursor.getString(14),
					cursor.getString(15), cursor.getString(16),
					cursor.getString(17), cursor.getString(18),
					cursor.getString(19), cursor.getInt(25), cursor.getInt(26),
					cursor.getInt(27), cursor.getString(28), cursor.getInt(29),
					cursor.getInt(30));

			result.add(tempObject);
		}
		return result;
	}

	public ArrayList<AdvisorType> getAllAdvisorType() {
		ArrayList<AdvisorType> result = new ArrayList<AdvisorType>();
		Cursor cursor = mDb.query(TableAdvisorType, AdvisorType, null, null,
				null, null, null);
		AdvisorType tempAdvisorType;
		while (cursor.moveToNext()) {
			tempAdvisorType = new AdvisorType(cursor.getInt(0),
					cursor.getString(1));
			result.add(tempAdvisorType);
		}

		return result;

	}

	public ArrayList<Executertype> getAllExecutertype() {
		ArrayList<Executertype> result = new ArrayList<Executertype>();
		Cursor cursor = mDb.query(TableExecutertype, Executertype, null, null,
				null, null, null);
		Executertype tempExecutertype;
		while (cursor.moveToNext()) {
			tempExecutertype = new Executertype(cursor.getInt(0),
					cursor.getString(1));
			result.add(tempExecutertype);
		}

		return result;

	}

	public ArrayList<News> getAllNews() {

		ArrayList<News> result = new ArrayList<News>();
		Cursor cursor = mDb
				.query(TableNews, News, null, null, null, null, null);
		News tempNews;
		while (cursor.moveToNext()) {
			tempNews = new News(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3), cursor.getInt(4));
			result.add(tempNews);
		}

		return result;

	}

	public News getNewsById(int Id) {
		News item = null;
		Cursor mnew = mDb.query("News", News, " Id=?",
				new String[] { String.valueOf(Id) }, null, null, null);

		if (mnew.moveToNext()) {
			item = CursorToNews(mnew);

		}

		return item;

	}

	public ArrayList<NewsPaper> getAllNewsPaper() {

		ArrayList<NewsPaper> result = new ArrayList<NewsPaper>();
		Cursor cursor = mDb.query(TableNewsPaper, NewsPaper, null, null, null,
				null, null);
		NewsPaper tempNewsPaper;
		while (cursor.moveToNext()) {
			tempNewsPaper = new NewsPaper(cursor.getInt(0),
					cursor.getString(1), cursor.getInt(2), cursor.getString(3),
					cursor.getString(4));
			result.add(tempNewsPaper);
		}

		return result;

	}

	private SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		return null;
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

	public ArrayList<Anad> getAnadById(int ProvinceId) {

		ArrayList<Anad> result = new ArrayList<Anad>();
		Anad item = null;
		Cursor mCur = mDb.query("Anad", Anad, "ProvinceId=?",
				new String[] { String.valueOf(ProvinceId) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToAnad(mCur);
			result.add(item);
		}

		return result;

	}

	public ArrayList<NewsPaper> getNewsPaperTypeId(int TypeId) {

		ArrayList<NewsPaper> result = new ArrayList<NewsPaper>();
		NewsPaper item = null;
		Cursor mCur = mDb.query("NewsPaper", NewsPaper, "TypeId=?",
				new String[] { String.valueOf(TypeId) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToNewsPaper(mCur);
			result.add(item);
		}

		return result;

	}

	public NewsPaper getNewsPaperId(int Id) {

		NewsPaper item = null;
		Cursor mCur = mDb.query("NewsPaper", NewsPaper, "Id=?",
				new String[] { String.valueOf(Id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToNewsPaper(mCur);
		}

		return item;

	}

	public ArrayList<Ticket> getTicketByTypeId(int TypeId) {

		ArrayList<Ticket> result = new ArrayList<Ticket>();
		Ticket item = null;
		Cursor mCur = mDb.query(TableTicket, Ticket, "TypeId=?",
				new String[] { String.valueOf(TypeId) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToTicket(mCur);
			result.add(item);
		}

		return result;

	}

	public Ticket getTicketById(int Id) {

		Ticket item = null;
		Cursor mCur = mDb.query(TableTicket, Ticket, "Id=?",
				new String[] { String.valueOf(Id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToTicket(mCur);
		}

		return item;

	}

	public ArrayList<Ticket> getFavoriteById(int TypeId, int provinceID) {

		ArrayList<Ticket> result = new ArrayList<Ticket>();
		Ticket item = null;

		Cursor mCur = mDb.query(
				TableTicket,
				Ticket,
				"TypeId=? AND ProvinceId=?",
				new String[] { String.valueOf(TypeId),
						String.valueOf(provinceID) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToTicket(mCur);
			result.add(item);
		}

		return result;

	}

	public Favorite f(int Id) {

		Favorite item = null;
		Cursor mCur = mDb.query(TableFavorite, Favorite, "Id=?",
				new String[] { String.valueOf(Id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorFavorite(mCur);
		}

		return item;

	}

	public Favorite getFavoriteByIdTicket(int Id) {

		Favorite item = null;
		Cursor mCur = mDb.query(TableFavorite, Favorite, "IdTicket=?",
				new String[] { String.valueOf(Id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorFavorite(mCur);
		}

		return item;

	}

	public Object getObjectByname(String name) {

		Object item = null;
		Cursor mCur = mDb.query(TableObject, Object, "Id=?",
				new String[] { String.valueOf(name) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToObject(mCur);
		}

		return item;

	}

	public ArrayList<Ticket> getTicketByTypeIdProId(int TypeId, int provinceID) {

		ArrayList<Ticket> result = new ArrayList<Ticket>();
		Ticket item = null;

		Cursor mCur = mDb.query(
				TableTicket,
				Ticket,
				"TypeId=? AND ProvinceId=?",
				new String[] { String.valueOf(TypeId),
						String.valueOf(provinceID) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToTicket(mCur);
			result.add(item);
		}

		return result;

	}

	public ArrayList<Ticket> getTicketByticketIduserId(int ticket, int user) {

		ArrayList<Ticket> result = new ArrayList<Ticket>();
		Ticket item = null;

		Cursor mCur = mDb.query(TableTicket, Ticket, "Id=? AND UserId=?",
				new String[] { String.valueOf(ticket), String.valueOf(user) },
				null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToTicket(mCur);
			result.add(item);
		}

		return result;

	}

	public ArrayList<Anad> getAnadtByTypeIdProId(int provinceID) {

		ArrayList<Anad> result = new ArrayList<Anad>();
		Anad item = null;

		Cursor mCur = mDb.query(TableAnad, Anad, " ProvinceId=?",
				new String[] { String.valueOf(provinceID) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToAnad(mCur);
			result.add(item);
		}

		return result;

	}

	public ArrayList<Ticket> getTicketByProvinceId(int ProvinceId) {

		ArrayList<Ticket> result = new ArrayList<Ticket>();
		Ticket item = null;
		Cursor mCur = mDb.query(TableTicket, Ticket, "ProvinceId=?",
				new String[] { String.valueOf(ProvinceId) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToTicket(mCur);
			result.add(item);
		}

		return result;

	}

	public ArrayList<City> getCitysByProvinceId(int ProvinceId) {

		ArrayList<City> result = new ArrayList<City>();
		City item = null;
		Cursor mCur = mDb.query("City", CityColumn, "ProvinceId=?",
				new String[] { String.valueOf(ProvinceId) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToCity(mCur);
			result.add(item);
		}
		Collections.sort(result);

		return result;

	}

	public ArrayList<AdvisorType> getAdvisorTypes() {

		ArrayList<AdvisorType> result = new ArrayList<AdvisorType>();
		AdvisorType item = null;
		Cursor mCur = mDb.query("AdvisorType", AdvisorType, null, null, null,
				null, null);

		while (mCur.moveToNext()) {
			item = CursorToAdvisorType(mCur);
			result.add(item);
		}

		return result;

	}

	private ListItem CursorToListItem(Cursor mCur) {

		ListItem item = new ListItem(mCur.getInt(0), mCur.getString(1),
				mCur.getInt(2));

		return item;
	}

	private Province CursorToProvince(Cursor cursor) {
		Province tempProvince = new Province(cursor.getInt(0),
				cursor.getString(1), cursor.getInt(2));
		return tempProvince;

	}

	@SuppressWarnings("unused")
	private Users CursorToUsers(Cursor cursor) {

		Users Users = new Users(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4),
				cursor.getString(5), cursor.getString(6), cursor.getString(7),
				cursor.getBlob(8), cursor.getInt(9), cursor.getString(10),
				cursor.getString(11), cursor.getInt(12), cursor.getInt(13));

		return Users;

	}

	@SuppressWarnings("unused")
	private LikeInObject CursorToLikeInObject(Cursor cursor) {
		LikeInObject tempProvince = new LikeInObject(cursor.getInt(0),
				cursor.getInt(1), cursor.getInt(2), cursor.getString(3),
				cursor.getInt(4), cursor.getInt(5));
		return tempProvince;

	}

	@SuppressWarnings("unused")
	private LikeInPaper CursorToLikeInPaper(Cursor cursor) {
		LikeInPaper temp = new LikeInPaper(cursor.getInt(0), cursor.getInt(1),
				cursor.getInt(2), cursor.getString(3), cursor.getInt(4),
				cursor.getInt(5));

		return temp;

	}

	@SuppressWarnings("unused")
	private LikeInComment CursorToLikeInComment(Cursor cursor) {
		LikeInComment temp = new LikeInComment(cursor.getInt(0),
				cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
		return temp;

	}

	private City CursorToCity(Cursor cursor) {
		City tempCity = new City(cursor.getInt(0), cursor.getString(1),
				cursor.getInt(2), cursor.getInt(3));
		return tempCity;
	}

	private Anad CursorToAnad(Cursor cursor) {
		Anad tempAnad = new Anad(cursor.getInt(0), cursor.getInt(2),
				cursor.getBlob(1), cursor.getString(3), cursor.getInt(4),
				cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));

		return tempAnad;
	}

	private Object CursorToObject(Cursor cursor) {
		Object tempObject = new Object(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4),
				cursor.getString(5), cursor.getBlob(6), cursor.getBlob(7),
				cursor.getBlob(8), cursor.getBlob(9), cursor.getString(10),
				cursor.getString(11), cursor.getString(12),
				cursor.getString(13), cursor.getString(14),
				cursor.getString(15), cursor.getInt(16), cursor.getInt(17),
				cursor.getString(18), cursor.getString(19),
				cursor.getString(20), cursor.getString(21),
				cursor.getString(22), cursor.getString(23), cursor.getInt(24),
				cursor.getInt(25), cursor.getInt(26), cursor.getString(27),
				cursor.getInt(28), cursor.getInt(29));
		return tempObject;
	}

	@SuppressWarnings("unused")
	private NewsPaper CursorToNewsPaper(Cursor cursor) {
		NewsPaper tempNewsPaper = new NewsPaper(cursor.getInt(0),
				cursor.getString(1), cursor.getInt(2), cursor.getString(3),
				cursor.getString(4));
		return tempNewsPaper;
	}

	private AdvisorType CursorToAdvisorType(Cursor cursor) {
		AdvisorType tempAdvisorType = new AdvisorType(cursor.getInt(0),
				cursor.getString(1));
		return tempAdvisorType;

	}

	private Executertype CursorToExecutertype(Cursor cursor) {
		Executertype tempExecutertype = new Executertype(cursor.getInt(0),
				cursor.getString(1));
		return tempExecutertype;

	}

	private Froum CursorToFroum(Cursor cursor) {
		Froum tempForum = new Froum(cursor.getInt(0), cursor.getInt(1),
				cursor.getString(2), cursor.getString(3), cursor.getInt(4),
				cursor.getString(5), cursor.getInt(6), cursor.getString(7));
		return tempForum;

	}

	@SuppressWarnings("unused")
	private Paper CursorToPaper(Cursor cursor) {
		Paper tempPaper = new Paper(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getInt(3), cursor.getString(4),
				cursor.getInt(5));
		return tempPaper;

	}

	private News CursorToNews(Cursor cursor) {
		News tempNews = new News(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getInt(4));
		return tempNews;

	}

	@SuppressWarnings("unused")
	private CommentInObject CursorToCommentInObject(Cursor cursor) {
		CommentInObject tempNews = new CommentInObject(cursor.getInt(0),
				cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
				cursor.getString(4), cursor.getInt(5), cursor.getInt(6));
		return tempNews;

	}

	@SuppressWarnings("unused")
	private Ticket CursorToTicket(Cursor cursor) {
		Ticket tempTicket = new Ticket(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getInt(3), cursor.getBlob(4),
				cursor.getString(5), cursor.getInt(6), cursor.getInt(7),
				cursor.getInt(8), cursor.getInt(9), cursor.getInt(10),
				cursor.getInt(11), cursor.getInt(12), cursor.getString(13),
				cursor.getString(14), cursor.getString(15),

				cursor.getString(16), cursor.getString(17), cursor.getBlob(18),
				cursor.getString(19), cursor.getInt(20), cursor.getInt(21));

		return tempTicket;

	}

	@SuppressWarnings("unused")
	private Favorite CursorFavorite(Cursor cursor) {
		Favorite tempFavorite = new Favorite(cursor.getInt(0),
				cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));

		return tempFavorite;

	}

	@SuppressWarnings("unused")
	private TicketType CursorToTicketType(Cursor cursor) {
		TicketType tempTicket = new TicketType(cursor.getInt(0),
				cursor.getString(1));
		return tempTicket;

	}

	public ArrayList<Province> getAllProvinceName() {
		ArrayList<Province> result = new ArrayList<Province>();
		Cursor cursor = mDb.query(TableProvince, Province, null, null, null,
				null, null);
		Province tempProvince;
		while (cursor.moveToNext()) {
			tempProvince = new Province(cursor.getInt(0), cursor.getString(1),
					cursor.getInt(2));
			result.add(tempProvince);
		}

		Collections.sort(result);
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

	public ArrayList<RowMain> getAllObjectName() {
		ArrayList<RowMain> result = new ArrayList<RowMain>();
		Cursor cursor = mDb.query(TableObject, Object, null, null, null, null,
				null);
		RowMain tempObject;
		while (cursor.moveToNext()) {
			tempObject = new RowMain(cursor.getString(1));
			result.add(tempObject);
		}

		return result;

	}

	public ArrayList<AdvisorType> getAllAdvisorTypeName() {
		ArrayList<AdvisorType> result = new ArrayList<AdvisorType>();
		Cursor cursor = mDb.query(TableAdvisorType, AdvisorType, null, null,
				null, null, null);
		AdvisorType tempAdvisorType;
		while (cursor.moveToNext()) {
			tempAdvisorType = new AdvisorType(cursor.getInt(0),
					cursor.getString(1));
			result.add(tempAdvisorType);
		}
		return result;
	}

	public ArrayList<Executertype> getAllExecutertypeName() {
		ArrayList<Executertype> result = new ArrayList<Executertype>();
		Cursor cursor = mDb.query(TableExecutertype, Executertype, null, null,
				null, null, null);
		Executertype tempExecutertype;
		while (cursor.moveToNext()) {
			tempExecutertype = new Executertype(cursor.getInt(0),
					cursor.getString(1));
			result.add(tempExecutertype);
		}
		return result;
	}

	public ArrayList<News> getAllNewsName() {
		ArrayList<News> result = new ArrayList<News>();
		Cursor cursor = mDb
				.query(TableNews, News, null, null, null, null, null);
		News tempNews;
		while (cursor.moveToNext()) {
			tempNews = new News(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3), cursor.getInt(4));
			result.add(tempNews);
		}
		return result;
	}

	public ArrayList<Paper> getAllPaper() {
		ArrayList<Paper> result = new ArrayList<Paper>();
		Cursor cursor = mDb.query(TablePaper, Paper, null, null, null, null,
				null);
		Paper tempObject;
		while (cursor.moveToNext()) {
			tempObject = new Paper(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getInt(3), cursor.getString(4),
					cursor.getInt(5));
			result.add(tempObject);
		}

		return result;

	}

	public Integer province_count(String table) {

		Cursor cu = mDb.rawQuery("select * from " + table + " group by Name",
				null);
		int s = cu.getCount();
		return s;
	}

	// ///////////
	// public Integer Image(String table) {
	//
	// Cursor cu = mDb.rawQuery("select * from " + table + " group by Name",
	// null);
	// int s = cu.getCount();
	// return s;
	// }
	public byte[] Anad_Image(String table) {

		Cursor cu = mDb.rawQuery("select * from " + table + "", null);
		cu.moveToFirst();
		byte[] s;
		s = cu.getBlob(1);
		return s;

	}

	public Integer LikeInObject_count() {

		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableLikeInObject, null);
		int res = 0;
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public Integer LikeInFroum_count(int froumID) {

		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableLikeInFroum + " WHERE FroumId=" + froumID, null);
		int res = 0;
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public Integer LikeInPaper_count(int paperID) {

		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableLikeInPaper + " WHERE PaperId=" + paperID, null);
		int res = 0;
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public Integer CommentInObject_count() {

		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableCommentInObject, null);
		int res = 0;
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public Integer CommentInFroum_count(int froumID) {

		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableCommentInFroum + " WHERE FroumId=" + froumID
				+ " AND CommentID=0", null);
		int res = 0;
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public Integer CommentInPaper_count(int paperID) {

		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableCommentInPaper + " WHERE PaperId=" + paperID, null);
		int res = 0;
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
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

	public Integer Object_count(String table) {

		Cursor cu = mDb.rawQuery("select * from " + table + " group by Name",
				null);
		int s = cu.getCount();
		return s;
	}

	public String Object_display(String table, int row, int field) {

		Cursor cu = mDb.rawQuery("select * from " + table
				+ " group by Name order by ID", null);
		cu.moveToPosition(row);
		String s = cu.getString(field);
		return s;
	}

	public Integer AdvisorType_count(String table) {

		Cursor cu = mDb.rawQuery("select * from " + table + " group by Name",
				null);
		int s = cu.getCount();
		return s;
	}

	public String AdvisorType_display(String table, int row, int field) {

		Cursor cu = mDb.rawQuery("select * from " + table
				+ " group by Name order by ID", null);
		cu.moveToPosition(row);
		String s = cu.getString(field);
		return s;
	}

	public Integer Executertype_count(String table) {

		Cursor cu = mDb.rawQuery("select * from " + table + " group by Name",
				null);
		int s = cu.getCount();
		return s;
	}

	public String Executertype_display(String table, int row, int field) {

		Cursor cu = mDb.rawQuery("select * from " + table
				+ " group by Name order by ID", null);
		cu.moveToPosition(row);
		String s = cu.getString(field);
		return s;
	}

	public Integer News_count(String table) {

		Cursor cu = mDb.rawQuery("select * from " + table + " group by Name",
				null);
		int s = cu.getCount();
		return s;
	}

	public String News_display(String table, int row, int field) {

		Cursor cu = mDb.rawQuery("select * from " + table
				+ " group by Name order by ID", null);
		cu.moveToPosition(row);
		String s = cu.getString(field);
		return s;
	}

	/*
	 * public String getUseridFroum(){ ArrayList<Froum> result = new
	 * ArrayList<Froum>(); String[] s = new String[1]; s[0] = "UserId"; Cursor
	 * cursor = mDb.query(TableFroum,s , null,null , null, null, null); Froum
	 * tempFroum; if(cursor.moveToNext()){ tempFroum = new
	 * Froum(cursor.getInt(0), cursor.getInt(3),
	 * cursor.getString(2),cursor.getString(1) ); result.add(tempFroum); }
	 * 
	 * 
	 * return result;
	 * 
	 * }
	 */

	/*
	 * public ArrayList<Comment> getAllComment() { ArrayList<Comment> result =
	 * new ArrayList<Comment>(); Cursor cursor = mDb.query(TableComment,
	 * Comment, null, null, null, null, null); Comment tempComment; while
	 * (cursor.moveToNext()) { result.add(CursorToComment(cursor)); }
	 * 
	 * return result;
	 * 
	 * }
	 */

	/*
	 * public ArrayList<Comment> getAllCommentByPapaerId(int paperId) {
	 * ArrayList<Comment> result = new ArrayList<Comment>(); Cursor cursor =
	 * mDb.query(TableComment, Comment, null, null, null, null, null); Comment
	 * tempComment; while (cursor.moveToNext()) {
	 * result.add(CursorToComment(cursor)); } return result; }
	 */

	public ArrayList<CommentInObject> getAllCommentInObjectById(int ObjectID) {
		ArrayList<CommentInObject> result = new ArrayList<CommentInObject>();
		Cursor cursor = mDb.query(TableCommentInObject, CommentInObject,
				" ObjectId=?", new String[] { String.valueOf(ObjectID) }, null,
				null, null);
		CommentInObject tempComment;
		while (cursor.moveToNext()) {
			result.add(CursorToCommentInObject(cursor));
		}
		return result;
	}

	public ArrayList<LikeInObject> getAllLikeInObjectById(int ObjectID) {
		ArrayList<LikeInObject> result = new ArrayList<LikeInObject>();
		Cursor cursor = mDb.query(TableLikeInObject, LikeInObject,
				" ObjectId=?", new String[] { String.valueOf(ObjectID) }, null,
				null, null);

		while (cursor.moveToNext()) {
			result.add(CursorToLikeInObject(cursor));
		}
		return result;
	}

	public ArrayList<LikeInPaper> getLikeInPaperByUserId(int UserID) {
		ArrayList<LikeInPaper> result = new ArrayList<LikeInPaper>();
		Cursor cursor = mDb.query(TableLikeInPaper, LikeInPaper, " UserId=?",
				new String[] { String.valueOf(UserID) }, null, null, null);

		while (cursor.moveToNext()) {
			result.add(CursorToLikeInPaper(cursor));
		}
		return result;
	}

	public ArrayList<CommentInFroum> getCommentInFroumbyPaperid(int Froumid,
			int commentID) {

		ArrayList<CommentInFroum> result = new ArrayList<CommentInFroum>();
		CommentInFroum item = null;

		// Cursor tCur = mDb.rawQuery("Select FroumId From " +
		// TableCommentInFroum + " Where FroumId=" +String.valueOf(Froumid),null
		// );

		Cursor mCur = mDb.query(
				TableCommentInFroum,
				CommentInFroum,
				"FroumId=? AND CommentID=?",
				new String[] { String.valueOf(Froumid),
						String.valueOf(commentID) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToCommentInFroum(mCur);
			result.add(item);
		}

		return result;

	}

	public ArrayList<CommentInFroum> getReplyCommentbyCommentID(int Froumid,
			int Commentid) {

		ArrayList<CommentInFroum> result = new ArrayList<CommentInFroum>();
		CommentInFroum item = null;

		/*
		 * Cursor tCur = mDb.rawQuery("Select FroumId From " +
		 * TableCommentInFroum + " Where FroumId=" +String.valueOf(Froumid),null
		 * );
		 */

		Cursor mCur = mDb.query(
				TableCommentInFroum,
				CommentInFroum,
				"FroumId=? AND CommentId=?",
				new String[] { String.valueOf(Froumid),
						String.valueOf(Commentid) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToCommentInFroum(mCur);
			result.add(item);
		}

		return result;

	}

	/*
	 * public boolean isUserLikedComment(int userId, int CommentId) {
	 * 
	 * Cursor curs = mDb.rawQuery( "SELECT COUNT(*) AS NUM FROM " +
	 * TableLikeInComment + " WHERE UserId= " + String.valueOf(userId) +
	 * " AND CommentId=" + String.valueOf(CommentId) + " AND IsLike=" + "1",
	 * null); if (curs.moveToNext()) { int number = curs.getInt(0); if (number >
	 * 0) return true; } return false; }
	 */

	public ArrayList<CommentInPaper> getCommentInPaperbyPaperid(int Paperid) {

		ArrayList<CommentInPaper> result = new ArrayList<CommentInPaper>();
		CommentInPaper item = null;

		Cursor mCur = mDb.query(TableCommentInPaper, CommentInPaper,
				"PaperId=?", new String[] { String.valueOf(Paperid) }, null,
				null, null);

		while (mCur.moveToNext()) {
			item = CursorToCommentInPaper(mCur);
			result.add(item);
		}

		return result;

	}

	public CommentInFroum getCommentInFroumbyID(int ID) {

		CommentInFroum item = null;

		// Cursor tCur = mDb.rawQuery("Select FroumId From " +
		// TableCommentInFroum + " Where FroumId=" +String.valueOf(Froumid),null
		// );

		Cursor mCur = mDb.query(TableCommentInFroum, CommentInFroum, "ID=?",
				new String[] { String.valueOf(ID) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToCommentInFroum(mCur);

		}

		return item;

	}

	public void insertCmtLikebyid(int id, String numofLike, int UserId) {
		if (!isUserLikedComment(UserId, id, 1)) {
			ContentValues uc = new ContentValues();
			uc.put("NumOfLike", numofLike);
			mDb.update(TableCommentInFroum, uc, "ID=" + id, null);
		}
	}

	public void insertLikeInCommentToDb(int UserId, int ISLike, int CommentId) {

		ContentValues uc = new ContentValues();

		uc.put("UserId", UserId);

		uc.put("CommentId", CommentId);
		uc.put("IsLike", ISLike);

		long res = mDb.insert(TableLikeInComment, null, uc);
		long res2 = res;
	}

	// ///////////////////////////////////////////////////////////////////
	public boolean isUserLikedComment(int userId, int CommentId, int isLike) {

		Cursor curs = mDb.rawQuery(
				"SELECT COUNT(*) AS NUM FROM " + TableLikeInComment
						+ " WHERE UserId= " + String.valueOf(userId)
						+ " AND CommentId=" + String.valueOf(CommentId)
						+ " AND IsLike=" + isLike, null);
		if (curs.moveToNext()) {
			int number = curs.getInt(0);
			if (number > 0)
				return true;
		}
		return false;
	}

	public boolean isUserFavorite(int userId, int TicketId) {

		Cursor curs = mDb.rawQuery("SELECT COUNT(*) AS NUM FROM "
				+ TableFavorite + " WHERE UserId= " + String.valueOf(userId)
				+ " AND IdTicket=" + String.valueOf(TicketId), null);
		if (curs.moveToNext()) {
			int number = curs.getInt(0);
			if (number > 0)
				return true;
		}
		return false;
	}

	public boolean isUserDisLikedComment(int userId, int CommentId) {

		Cursor curs = mDb.rawQuery(
				"SELECT COUNT(*) AS NUM FROM " + TableLikeInComment
						+ " WHERE UserId= " + String.valueOf(userId)
						+ " AND CommentId=" + String.valueOf(CommentId)
						+ " AND IsLike=" + "0", null);
		if (curs.moveToNext()) {
			int number = curs.getInt(0);
			if (number > 0)
				return true;
		}
		return false;
	}

	public void insertCmtDisLikebyid(int id, String numofDisLike, int UserId) {
		if (!isUserDisLikedComment(UserId, id)) {
			ContentValues uc = new ContentValues();
			uc.put("NumOfDislike", numofDisLike);
			mDb.update(TableCommentInFroum, uc, "ID=" + id, null);
		}
	}

	public Users getUserbymobailenumber(String mobailenumber)

	{
		Users result = null;

		Cursor mCur = mDb.query(TableUsers, Users, "Mobailenumber=?",
				new String[] { String.valueOf(mobailenumber) }, null, null,
				null);
		if (mCur.moveToNext()) {
			result = CursorToUsers(mCur);
		}
		return result;

	}

	// ////////////

	// /////////////////////

	public Users getUserbyid(int id) {

		Users result = null;

		Cursor mCur = mDb.query(TableUsers, Users, " ID=?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (mCur.moveToNext()) {
			result = CursorToUsers(mCur);
		}
		return result;
	}

	public Froum getFroumItembyid(int Id) {

		Froum item = null;
		Cursor mCur = mDb.query("Froum", Froum, " Id=?",
				new String[] { String.valueOf(Id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToFroum(mCur);

		}

		return item;
	}

	public Froum getFroumTitlebyid(int Id) {

		Cursor cursor = mDb.query(TableFroum, Froum, null, null, null, null,
				null);
		Froum tempFroum = null;
		if (cursor.moveToNext()) {
			tempFroum = CursorToFroum(cursor);
		}

		return tempFroum;

	}

	public Paper getPaperItembyid(int Id) {

		Paper item = null;
		Cursor mCur = mDb.query(TablePaper, Paper, " Id=?",
				new String[] { String.valueOf(Id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToPaper(mCur);

		}

		return item;

	}

	public Object getObjectbyid(int id) {

		Object item = null;
		Cursor mCur = mDb.query(TableObject, Object, " Id=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToObject(mCur);

		}

		return item;

	}

	// ////////
	// public Object getobjectbyid(int id){
	//
	// Object it=null;
	//
	// Cursor mCur = mDb.query(TableObject, Object, "Id=?", new
	// String[]{String.valueOf(id)}, null, null, null);
	// if (mCur.moveToNext()) {
	// it = CursorToObject(mCur);
	// }
	//
	// return it;
	//
	//
	// }

	public ArrayList<Froum> getAllFroum() {
		ArrayList<Froum> result = new ArrayList<Froum>();
		Cursor cursor = mDb.query(TableFroum, Froum, null, null, null, null,
				null);
		Froum tempFroum;
		while (cursor.moveToNext()) {
			result.add(CursorToFroum(cursor));
		}
		return result;
	}

	public NewsPaper getAllNewsPaperid(int id) {

		NewsPaper item = null;
		Cursor mCur = mDb.query("NewsPaper", NewsPaper, " Id=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToNewsPaper(mCur);

		}

		return item;

	}

	public ArrayList<Ticket> getAllTicket() {
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		Cursor cursor = mDb.query(TableTicket, Ticket, null, null, null, null,
				null);
		Ticket tempTicket;
		while (cursor.moveToNext()) {
			result.add(CursorToTicket(cursor));
		}

		return result;

	}

	public ArrayList<TicketType> getAllTicketType() {
		ArrayList<TicketType> result = new ArrayList<TicketType>();
		Cursor cursor = mDb.query(TableTicketType, TicketType, null, null,
				null, null, null);
		while (cursor.moveToNext()) {
			result.add(CursorToTicketType(cursor));
		}

		return result;

	}

	public ArrayList<String> getAllObjectname() {
		ArrayList<String> result = new ArrayList<String>();
		Cursor cursor = mDb.query(TableObject, Object, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			result.add(CursorToObject(cursor).getName());
		}

		return result;

	}

	public ArrayList<String> getAllObjectid() {
		ArrayList<String> result = new ArrayList<String>();
		Cursor cursor = mDb.query(TableObject, Object, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			result.add(String.valueOf(CursorToObject(cursor).getId()));
		}

		return result;

	}

	public ArrayList<Anad> getAllAnad() {
		ArrayList<Anad> result = new ArrayList<Anad>();
		Cursor cursor = mDb
				.query(TableAnad, Anad, null, null, null, null, null);
		Anad tempAnad;
		while (cursor.moveToNext()) {
			result.add(CursorToAnad(cursor));
		}

		return result;

	}

	/*
	 * public String getUseridFroum(){ ArrayList<Froum> result = new
	 * ArrayList<Froum>(); String[] s = new String[1]; s[0] = "UserId"; Cursor
	 * cursor = mDb.query(TableFroum,s , null,null , null, null, null); Froum
	 * tempFroum; if(cursor.moveToNext()){ tempFroum = new
	 * Froum(cursor.getInt(0), cursor.getInt(3),
	 * cursor.getString(2),cursor.getString(1) ); result.add(tempFroum); }
	 * 
	 * 
	 * return result;
	 * 
	 * }
	 */

	public ArrayList<Integer> getUSeridComment() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		String[] s = new String[1];
		s[0] = "UserId";
		Cursor cursor = mDb
				.query(TableComment, s, null, null, null, null, null);

		while (cursor.moveToNext()) {
			Integer x = cursor.getInt(0);
			result.add(x);
		}

		return result;

	}

	public ArrayList<Users> getUserOfcomment(String froumId) {
		ArrayList<Users> result = new ArrayList<Users>();
		Cursor cursor = mDb
				.rawQuery(
						"Select "
								+ Users[0]
								+ ","
								+ Users[1]
								+ ","
								+ Users[2]
								+ ","
								+ Users[3]
								+ "  From Users inner join Comment on User.id=Comment.UserId where Comment.PaperId ="
								+ froumId, null);
		while (cursor.moveToNext()) {
			Users tempusers = new Users(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5),
					cursor.getString(6), cursor.getString(7),
					cursor.getBlob(8), cursor.getInt(9), cursor.getString(10),
					cursor.getString(11), cursor.getInt(12), cursor.getInt(13));
			result.add(tempusers);

		}
		return result;

	}

	public ArrayList<Ticket> getTicketByusetId(int userId) {
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		Cursor cursor = mDb
				.rawQuery(
						"select t.* from [Ticket] t inner join [Favorite] f on t.[Id] = f.[IdTicket] 	where f.[ObjectId] = 0 and f.[UserId] =	"
								+ userId, null);
		while (cursor.moveToNext()) {
			result.add(CursorToTicket(cursor));

		}
		return result;

	}

	public void deletebyIdTicket(int id) {

		mDb.execSQL("delete from [Favorite] where IdTicket = " + id);

	}

	private CommentInFroum CursorToCommentInFroum(Cursor cursor) {
		CommentInFroum tempComment = new CommentInFroum(cursor.getInt(0),
				cursor.getString(1), cursor.getInt(2), cursor.getInt(3),

				cursor.getString(4), cursor.getInt(5), cursor.getString(6),
				cursor.getString(7), cursor.getInt(8));

		return tempComment;

	}

	private CommentInPaper CursorToCommentInPaper(Cursor cursor) {
		CommentInPaper tempComment = new CommentInPaper(cursor.getInt(0),
				cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
				cursor.getString(4), cursor.getInt(5), cursor.getInt(6));

		return tempComment;

	}

	public ArrayList<Users> getUserOfcomment(int froumId) {
		ArrayList<Users> result = new ArrayList<Users>();
		Cursor cursor = mDb
				.rawQuery(
						"Select "
								+ Users[0]
								+ ","
								+ Users[1]
								+ ","
								+ Users[2]
								+ ","
								+ Users[3]
								+ "  From Users inner join Comment on User.id=Comment.UserId where Comment.PaperId ="
								+ froumId, null);
		while (cursor.moveToNext()) {
			Users tempusers = new Users(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),

					cursor.getString(4), cursor.getString(5),
					cursor.getString(6), cursor.getString(7),
					cursor.getBlob(8), cursor.getInt(9), cursor.getString(10),
					cursor.getString(11), cursor.getInt(12), cursor.getInt(13));

			result.add(tempusers);
		}
		return result;

	}

	public int getNumberOfListItemChilds(int parentId) {
		int res = 0;
		Cursor cursor = mDb.rawQuery("Select Count(*) From " + TableListItem
				+ " Where ListId= " + parentId, null);
		if (cursor.moveToNext()) {
			res = cursor.getInt(0);
		}

		return res;

	}

	public ArrayList<Object> getObjectbyParentId(int parentid) {

		ArrayList<Object> result = new ArrayList<Object>();
		Object item = null;

		Cursor mCur = mDb.query(TableObject, Object, "ParentId=?",
				new String[] { String.valueOf(parentid) }, null, null, null);

		while (mCur.moveToNext()) {
			item = CursorToObject(mCur);
			result.add(item);
		}

		return result;

	}

	public ArrayList<Object> getObjectBy_BTId_CityId(int Object_id, int City_id) {
		ArrayList<Object> result = new ArrayList<Object>();
		Cursor cursor = mDb
				.rawQuery(

						"Select O.Id, O.Name, O.Phone, O.Email, O.Fax,O.Description, O.Image1, O.Image2, O.Image3, O.Image4,O.Pdf1,O.Pdf2,O.Pdf3,O.Pdf4,O.Address,O.CellPhone,O.ObjectTypeId,O.ObjectBrandTypeId,O.Facebook,O.Instagram,O.LinkedIn,O.Google,O.Site,O.Twitter,O.rate,O. From "
								+ TableObject
								+ " as O inner join "
								+ TableObjectInCity
								+ " as C On O.Id = C.ObjectId Where O.ObjectBrandTypeId = "
								+ Object_id + " and C.CityId =" + City_id, null);
		Object tempObject;
		while (cursor.moveToNext()) {
			tempObject = new Object(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5), null, null, null,
					null, cursor.getString(6), cursor.getString(7),
					cursor.getString(8), cursor.getString(9),
					cursor.getString(10), cursor.getString(11),
					cursor.getInt(12), cursor.getInt(13), cursor.getString(14),
					cursor.getString(15), cursor.getString(16),
					cursor.getString(17), cursor.getString(18),
					cursor.getString(19), cursor.getInt(25), cursor.getInt(27),
					cursor.getInt(28), cursor.getString(29), cursor.getInt(30),
					cursor.getInt(31));

			result.add(tempObject);
		}
		return result;
	}

	public Users getUserById(int id) {
		Users item = null;
		Cursor mCur = mDb.query(TableUsers, Users, " Id=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToUsers(mCur);

		}

		return item;
	}

	public void UpdateObjectProperties(int id, String name, String Phone,
			String Email, String fax, String description, byte[] HeaderImage,
			byte[] ProfileImage, byte[] FooterImage, String LinkCatalog,
			String LinkPrice, String LinkPDF, String LinkVideo, String Address,
			String Mobile, String LinkFaceBook, String LinkInstagram,
			String LinkLinkedin, String LinkGoogle, String LinkSite,
			String LinkTweitter) {

		ContentValues uc = new ContentValues();

		if (!"".equals(name) && name != null)
			uc.put("Name", name);
		if (!"".equals(Phone) && Phone != null)
			uc.put(Object[2], Phone);
		if (!"".equals(Email) && Email != null)
			uc.put(Object[3], Email);
		if (!"".equals(fax) && fax != null)
			uc.put(Object[4], fax);
		if (!"".equals(description) && description != null)
			uc.put(Object[5], description);
		if (HeaderImage != null)
			uc.put(Object[6], HeaderImage);
		if (ProfileImage != null)
			uc.put(Object[7], ProfileImage);
		if (FooterImage != null)
			uc.put(Object[8], FooterImage);
		if (!"".equals(LinkCatalog) && LinkCatalog != null)
			uc.put(Object[10], LinkCatalog);
		if (!"".equals(LinkPrice) && LinkPrice != null)
			uc.put(Object[11], LinkPrice);
		if (!"".equals(LinkPDF) && LinkPDF != null)
			uc.put(Object[12], LinkPDF);
		if (!"".equals(LinkVideo) && LinkVideo != null)
			uc.put(Object[13], LinkVideo);
		if (!"".equals(Address) && Address != null)
			uc.put(Object[14], Address);
		if (!"".equals(Mobile) && Mobile != null)
			uc.put(Object[15], Mobile);
		if (!"".equals(LinkFaceBook) && LinkFaceBook != null)
			uc.put(Object[18], LinkFaceBook);
		if (!"".equals(LinkInstagram) && LinkInstagram != null)
			uc.put(Object[19], LinkInstagram);
		if (!"".equals(LinkLinkedin) && LinkLinkedin != null)
			uc.put(Object[20], LinkLinkedin);
		if (!"".equals(LinkGoogle) && LinkGoogle != null)
			uc.put(Object[21], LinkGoogle);
		if (!"".equals(LinkSite) && LinkSite != null)
			uc.put(Object[22], LinkSite);
		if (!"".equals(LinkTweitter) && LinkTweitter != null)
			uc.put(Object[23], LinkTweitter);

		mDb.update(TableObject, uc, "ID=" + id, null);
		Toast.makeText(mContext, "اطلاعات با موفقیت ویرایش شد",
				Toast.LENGTH_SHORT).show();
	}

	// /////////////////////////////////////ÈÏÓÊ ÂæÑÏä ÊÚÏÇÏ ÓØÑåÇ
	// ///////////////
	// /////////////////////////////////////ÈÏÓÊ ÂæÑÏä ÊÚÏÇÏ ÓØÑåÇ
	// ///////////////
	public int getcount() {
		int item = 0;

		// return item=mDb.rawQuery("Select Count(*) From " + TableUsers,
		// null).getCount();

		Cursor cursor = mDb
				.rawQuery("select count(*) from " + TableUsers, null);

		// ensure there is at least one row and one column
		cursor.moveToFirst();
		if (cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
			return cursor.getInt(0);
		} else {
			return 0;
		}

	}

	public void InsertInformationNewObject(String name, String Phone,
			String Email, String fax, String description, byte[] HeaderImage,
			byte[] ProfileImage, byte[] FooterImage, String LinkCatalog,
			String LinkPrice, String LinkPDF, String LinkVideo, String Address,
			String Mobile, String LinkFaceBook, String LinkInstagram,
			String LinkLinkedin, String LinkGoogle, String LinkSite,
			String LinkTweitter) {

		ContentValues cv = new ContentValues();

		if (!"".equals(name))
			cv.put(Object[1], name);
		if (!"".equals(Phone))
			cv.put(Object[2], Phone);
		if (!"".equals(Email))
			cv.put(Object[3], Email);
		if (!"".equals(fax))
			cv.put(Object[4], fax);
		if (!"".equals(description))
			cv.put(Object[5], description);
		if (HeaderImage != null)
			cv.put(Object[6], HeaderImage);
		if (ProfileImage != null)
			cv.put(Object[7], ProfileImage);
		if (FooterImage != null)
			cv.put(Object[8], FooterImage);
		if (!"".equals(LinkCatalog))
			cv.put(Object[10], LinkCatalog);
		if (!"".equals(LinkPrice))
			cv.put(Object[11], LinkPrice);
		if (!"".equals(LinkPDF))
			cv.put(Object[12], LinkPDF);
		if (!"".equals(LinkVideo))
			cv.put(Object[13], LinkVideo);
		if (!"".equals(Address))
			cv.put(Object[14], Address);
		if (!"".equals(Mobile))
			cv.put(Object[15], Mobile);
		if (!"".equals(LinkFaceBook))
			cv.put(Object[18], LinkFaceBook);
		if (!"".equals(LinkInstagram))
			cv.put(Object[19], LinkInstagram);
		if (!"".equals(LinkLinkedin))
			cv.put(Object[20], LinkLinkedin);
		if (!"".equals(LinkGoogle))
			cv.put(Object[21], LinkGoogle);
		if (!"".equals(LinkSite))
			cv.put(Object[22], LinkSite);
		if (!"".equals(LinkTweitter))
			cv.put(Object[23], LinkTweitter);

		mDb.insert(TableObject, null, cv);
		Toast.makeText(mContext, "اطلاعات با موفقیت ثبت شد", Toast.LENGTH_SHORT)
				.show();

	}

	// //////////////////////////
	public void UpdateProvinceToDb(int id, int count) {

		ContentValues uc = new ContentValues();
		// uc.put("Name", name);
		// uc.put("Email", email);
		// uc.put("Password", password);
		uc.put("Count", count);

		mDb.update(TableProvince, uc, "ID =" + id, null);

	}

	// ///////////////////////////////////////////////////////
	public City getCityById(int id) {
		City item = null;
		Cursor mCur = mDb.query(TableCity, CityColumn, " Id=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToCity(mCur);

		}

		return item;
	}

	// /////////////////////////////////////////////////////////////
	public void UpdateCityToDb(int id, int count) {

		ContentValues uc = new ContentValues();
		// uc.put("Name", name);
		// uc.put("Email", email);
		// uc.put("Password", password);
		uc.put("Count", count);

		mDb.update(TableCity, uc, "ID =" + id, null);
	}

	// ///////////////////////////////////////////////////
	public Province getProvinceById(int id) {
		Province item = null;
		Cursor mCur = mDb.query(TableCity, Province, " Id=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToProvince(mCur);

		}

		return item;
	}

	public Object getObjectByName(String name) {
		Object item = null;
		Cursor mCur = mDb.query(TableObject, Object, " Name=?",
				new String[] { String.valueOf(name) }, null, null, null);

		if (mCur.moveToNext()) {
			item = CursorToObject(mCur);

		}
		return item;

	}

	public Integer getCountofCommentinFroumObject(int froumID, int CommentID) {

		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableCommentInFroum + " WHERE FroumId=" + froumID
				+ " AND CommentID=" + CommentID, null);
		int res = 0;
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public void deleteLikeFromCommentInFroum(int CommentID, int userID,
			int isLike) {
		String[] t = { String.valueOf(CommentID), String.valueOf(userID),
				String.valueOf(isLike) };
		mDb.delete(TableLikeInComment,
				"CommentId=? and UserId =? and IsLike =?", t);
	}

	public int NumOfNewLikeInObject() {
		int res = 0;
		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableLikeInObject + " WHERE Seen=0", null);
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public int NumOfNewLikeInPaper() {
		int res = 0;
		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableLikeInPaper + " WHERE Seen=0", null);
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public int NumOfNewLikeInFroum() {
		int res = 0;
		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableLikeInFroum + " WHERE Seen=0", null);
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public int NumOfNewCmtInObject() {
		int res = 0;
		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableCommentInObject + " WHERE Seen=0", null);
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public int NumOfNewCmtInPaper() {
		int res = 0;
		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableCommentInPaper + " WHERE Seen=0", null);
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	public int NumOfNewCmtInFroum() {
		int res = 0;
		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableCommentInFroum + " WHERE Seen=0", null);
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

	
	public Users getCurrentUser() {
		Users u = null;
		Cursor mCur = mDb.query(TableUsers, Users, "WHERE admin=1", null, null,
				null, null);
		if (mCur.moveToNext()) {
			u = CursorToUsers(mCur);
		}
		return u;
	}
	public Integer Province_serach(String word, String field) {

		Cursor cu;
		if (field.equals("Name")) {
			cu = mDb.rawQuery("select * from Province where " + field + " Like '%" + word + "%' group by Name", null);
		} else {
			cu = mDb.rawQuery("select * from content where " + field
					+ " Like '%" + word + "%'", null);
		}

		int s = cu.getCount();
		return s;
	}

	public String serach(int row, int col, String word, String field) {

		Cursor cu;
		if (field.equals("Name")) {
			cu = mDb.rawQuery("select * from Province where " + field
					+ " Like '%" + word + "%' group by Name", null);
		} else {
			cu = mDb.rawQuery("select * from Province where " + field
					+ " Like '%" + word + "%'", null);
		}

		cu.moveToPosition(row);
		String s = cu.getString(col);
		return s;
	}

	public Integer count_search(String word, string field) {

		Cursor cu;
		if (field.equals("Name")) {
			cu = mDb.rawQuery("select * from content where " + field
					+ " Like '%" + word + "%' group by Name", null);
		} else {
			cu = mDb.rawQuery("select * from content where " + field
					+ " Like '%" + word + "%'", null);
		}

		int s = cu.getCount();
		return s;
	}

	

	public void deleteLikeFromFroum(int userID, int FroumId) {
		String[] t = { String.valueOf(userID), String.valueOf(FroumId) };
		mDb.delete(TableLikeInFroum, "UserId=? and FroumId=?", t);
	}

	public Integer getCountOfReplyInFroum(int froumID, int commentID) {

		Cursor cu = mDb.rawQuery("Select count(*) as co from "
				+ TableCommentInFroum + " WHERE FroumId=" + froumID
				+ " AND CommentID= " + commentID, null);
		int res = 0;
		if (cu.moveToNext()) {
			res = cu.getInt(0);
		}
		return res;
	}

}
