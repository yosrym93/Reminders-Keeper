package com.example.reminder;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class RemindersDatabaseManager {

    //these are the column names
    private static final String COL_ID = "_id";
    static final String COL_CONTENT = "content";
    static final String COL_IMPORTANT = "important";
    //these are the corresponding indices
    static final int INDEX_ID = 0;
    static final int INDEX_CONTENT = INDEX_ID + 1;
    static final int INDEX_IMPORTANT = INDEX_ID + 2;
    //used for logging
    private static final String TAG = "RemindersDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "dba_remdrs";
    private static final String TABLE_NAME = "tbl_remdrs";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_CONTENT + " TEXT, " +
                    COL_IMPORTANT + " INTEGER );";


    RemindersDatabaseManager(Context ctx) {
        this.mCtx = ctx;
    }

    void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }


    public void createReminder(String name, boolean important) {
        String imp;
        if (important){
            imp = "1";
        }
        else
            imp = "0";
        String Query = "INSERT INTO "+TABLE_NAME+"("+COL_CONTENT+","+COL_IMPORTANT+") VALUES('"+name+"',"+imp+");";
        mDb.execSQL(Query);
      
    }

    void createReminder(Reminder reminder) {
        String imp;
        if (reminder.getImportant()){
            imp = "1";
        }
        else
            imp = "0";
        String Query = "INSERT INTO "+TABLE_NAME+"("+COL_CONTENT+","+COL_IMPORTANT+") VALUES('"+reminder.getContent()+"',"+imp+");";
        mDb.execSQL(Query);
    }

    Reminder fetchReminderById(int id) {
       String Query = "Select * from "+TABLE_NAME+" where "+COL_ID+" = "+ id;
       Cursor result = mDb.rawQuery(Query,null);
       result.moveToFirst();
       boolean  imp = false;
       if (result.getString(INDEX_IMPORTANT).equals("1")){
           imp = true;
       }
       Reminder r = new Reminder(id,result.getString(INDEX_CONTENT),imp);
       result.close();
       return r;
    }


    Cursor fetchAllReminders() {
        String Query = "Select * from "+TABLE_NAME+";";
         return mDb.rawQuery(Query,null);
    }

    void updateReminder(Reminder reminder) {
        int imp = 0;
        if(reminder.getImportant())
            imp = 1;
       String Query ="UPDATE "+TABLE_NAME+" SET "+COL_CONTENT +" = '"+reminder.getContent() +"', "+COL_IMPORTANT+" = "+imp;
       Query = Query + " WHERE "+COL_ID+" = "+ reminder.getId();
       mDb.execSQL(Query);
    }
    void deleteReminderById(int nId) {

        String Query ="DELETE FROM "+TABLE_NAME+" WHERE "+COL_ID+" = "+ nId;
        mDb.execSQL(Query);
    }

    public void deleteAllReminders() {

        String Query ="DELETE FROM "+TABLE_NAME;
        mDb.execSQL(Query);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


}
