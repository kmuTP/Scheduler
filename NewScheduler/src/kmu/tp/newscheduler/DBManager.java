package kmu.tp.newscheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DBManager extends SQLiteOpenHelper {
 
    public DBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    	//스케줄 테이블 생성
    	db.execSQL("create table schedule ("
    			+ "no integer primary key autoincrement,"
    			+ "subject text,"
    			+ "startdate blob,"
    			+ "enddate blob,"
    			+ "content text,"
    			+ "favorite integer);");
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}