package com.project.mechanic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

	 public class Database extends SQLiteOpenHelper {

	 	
		public final String path="data/data/com.project.mechanic/databases/";
	 	public final String Name="Mechanical";
	 	public SQLiteDatabase mydb;
	 	
	 	private final Context mycontext;
	 	
	 	
	 	public Database(Context context) {
	 		super(context, "Mechanical", null, 1);
	 		mycontext=context;
	 		
	 	}

	 	
	 	@Override
	 	public void onCreate(SQLiteDatabase arg0) {
	 
	 	}
	 	@Override
	 	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	 		
	 		
	 	}
	 	
	 	
	 	public void useable(){
	 		
	 		boolean checkdb=checkdb();
	 		
	 		if(checkdb){

	 		}else{
	 			
	 			this.getReadableDatabase();
	 			
	 			try{
	 			copydatabase();
	 			}catch(IOException e){
	 			}
	 			
	 		}
	 		
	 		
	 		
	 	}
	 	
	 	public void open(){
	 		
	 		mydb=SQLiteDatabase.openDatabase(path+Name, null, SQLiteDatabase.OPEN_READWRITE);
	 		
	 	}
	 	
	 	public void close(){
	 		mydb.close();
	 	}
	 	
	 	public boolean checkdb(){
	 		
	 		SQLiteDatabase db=null;
	 		try{	
	 		db=SQLiteDatabase.openDatabase(path+Name, null, SQLiteDatabase.OPEN_READONLY);
	 		}
	 		catch(SQLException e)
	 		{

	 		}
	 		
	 		return db !=null ? true:false ;
	 		
	 	}
	 	
	 	public void copydatabase() throws IOException{
	 		OutputStream myOutput = new FileOutputStream(path+Name);
	 		byte[] buffer = new byte[1024];
	 		int length;
	 		InputStream myInput = mycontext.getAssets().open(Name);
	 		while ((length = myInput.read(buffer)) > 0) {
	 		myOutput.write(buffer, 0, length);
	 		}
	 		myInput.close();
	 		myOutput.flush();
	 		myOutput.close();
	 	}
	 	
	 	
	 	public byte[] getpic(String table,String sea,String name,String page){
	         
	 	     Cursor cu=mydb.rawQuery("select * from "+table+" where Seasone='"+sea+"' and Name='"+name+"' and Page="+page, null);
	 	     cu.moveToFirst();
	 	     byte[] s;
	 	     s=cu.getBlob(6);
	 	     return s;
	 	       
	 	     }
	 	
	 	
	 	public String Display(int row,int field,String table){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table, null);
	 		cu.moveToPosition(row);
	 		String s=cu.getString(field);
	 		return s;
	 	}

	 	
	 	public Integer count(String table,String field){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" group by "+field, null);
	 		int s=cu.getCount();
	 		return s;
	 	}
	 	
	 	public String Season_display(String table,int row){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" group by Seasone order by id", null);
	 		cu.moveToPosition(row);
	 		String s=cu.getString(4);
	 		return s;
	 	}
	 	
	 	public Integer Story_count(String table,String sea){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" where Seasone='"+sea+"' group by Name", null);
	 		int s=cu.getCount();
	 		return s;
	 	}
	 	
	 	public String Story_display(String table,int row,String sea){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" where Seasone='"+sea+"' group by Name order by ID", null);
	 		cu.moveToPosition(row);
	 		String s=cu.getString(1);
	 		return s;
	 	}
	 	
	 	
	 	public Integer Story_page_count(String table,String sea,String story){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" where Seasone='"+sea+"' and Name='"+story+"'", null);
	 		int s=cu.getCount();
	 		return s;
	 	}
	 	
	 	public String main_display(String table,String sea,String name,String page){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" where Seasone='"+sea+"' and Name='"+name+"' and Page="+page, null);
	 		cu.moveToFirst();
	 		String s=cu.getString(2);
	 		return s;
	 	}

	 	
	 	
	 	public void fav_update(String table,String sea, String name,String v){
	 		ContentValues cv=new ContentValues();
	 		cv.put("Fav", v);
	 		mydb.update(table, cv, "Seasone='"+sea+"' and Name='"+name+"'", null);
	 		

	 	}
	 	
	 	public Integer fav_count(String table){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" where Fav=1 group by Name", null);
	 		int s=cu.getCount();
	 		return s;
	 	}
	 	
	 	
	 	public String fav_display(String table,int row,int field){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" where Fav=1 group by Name", null);
	 		cu.moveToPosition(row);
	 		String s=cu.getString(field);
	 		return s;
	 	}
	 	
	 	
	 	public Integer count_serach(String word,String field){
	 		
	 		Cursor cu;
	 		if(field.equals("Name")){
	 			cu=mydb.rawQuery("select * from content where "+field+" Like '%"+word+"%' group by Name", null);
	 		}else{
	 			cu=mydb.rawQuery("select * from content where "+field+" Like '%"+word+"%'", null);
	 		}
	 		
	 		int s=cu.getCount();
	 		return s;
	 	}
	 	
	 	
	 	public String serach(int row,int col,String word,String field){
	 		
	 		Cursor cu;
	 		if(field.equals("Name")){
	 			cu=mydb.rawQuery("select * from content where "+field+" Like '%"+word+"%' group by Name", null);
	 		}else{
	 			cu=mydb.rawQuery("select * from content where "+field+" Like '%"+word+"%'", null);
	 		}
	 		
	 		
	 		cu.moveToPosition(row);
	 		String s=cu.getString(col);
	 		return s;
	 	}
	 	
	 	
	 public Integer count_search(String word,string field){
	 		
	 	Cursor cu;
	 	if(field.equals("Name")){
	 	 cu=mydb.rawQuery("select * from content where "+field+" Like '%"+word+"%' group by Name", null);
	 	}else{
	 		cu=mydb.rawQuery("select * from content where "+field+" Like '%"+word+"%'", null);
	 	}
	 	 
	 		int s=cu.getCount();
	 		return s;
	 	}
	 	
	 	
	 	public String search(int row,int col,String word,string field){
	 		Cursor cu;
	 		if(field.equals("Name")){
	 		 cu=mydb.rawQuery("select * from content where "+field+" Like '%"+word+"%' group by Name", null);
	 		}else{
	 			cu=mydb.rawQuery("select * from content where "+field+" Like '%"+word+"%'", null);
	 		}
	 		 
	 		 
	 		cu.moveToPosition(row);
	 		String s=cu.getString(col);
	 		return s;
	 	}
	 	
	 public Integer all_story_count(String table){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" group by Name", null);
	 		int s=cu.getCount();
	 		return s;
	 	}
	 	
	 	public String all_story_display(String table,int row,int field){
	 		
	 		Cursor cu=mydb.rawQuery("select * from "+table+" group by Name order by ID", null);
	 		cu.moveToPosition(row);
	 		String s=cu.getString(field);
	 		return s;
	 	}

	 	

	 }





