package com.example.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.reminder.Reminder;


public class RemindersDbAdapter {

    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMPORTANT = "important";
    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;
    public static final int INDEX_IMPORTANT = INDEX_ID + 2;
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


    public RemindersDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }
    //close
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }


    //TODO implement the function createReminder() which take the name as the content of the reminder and boolean important...note that the id will be created for you automatically
    public void createReminder(String name, boolean important) {
        String imp;
        if (important == true){
            imp = "1";
        }
        else
            imp = "0";
        String Query = "INSERT INTO "+TABLE_NAME+"("+COL_CONTENT+","+COL_IMPORTANT+") VALUES('"+name+"',"+imp+");";
        System.out.println(Query);
        mDb.execSQL(Query);
      
    }
    //TODO overloaded to take a reminder
    public long createReminder(Reminder reminder) {
        String imp;
        if (reminder.getImportant() == true){
            imp = "1";
        }
        else
            imp = "0";
        String Query = "INSERT INTO "+TABLE_NAME+"("+COL_CONTENT+","+COL_IMPORTANT+") VALUES("+reminder.getContent()+","+imp+");";
        mDb.execSQL(Query);
        return 1;
    }

    //TODO implement the function fetchReminderById() to get a certain reminder given its id
    public Reminder fetchReminderById(int id) {
       String Query = "Select * from "+TABLE_NAME+" where "+COL_ID+" = "+String.valueOf(id);
       Cursor result = mDb.rawQuery(Query,null);
       result.moveToFirst();
       boolean  imp = false;
       if (result.getString(INDEX_IMPORTANT).equals("1")){
           imp = true;
       }
       Reminder r = new Reminder(id,result.getString(INDEX_CONTENT),imp);
       System.out.println(r.getContent());
       return r;
    }


    //TODO implement the function fetchAllReminders() which get all reminders
    public Cursor fetchAllReminders() {
        String Query = "Select * from "+TABLE_NAME+";";
        Cursor result = mDb.rawQuery(Query,null);
        return result;
    }

    //TODO implement the function updateReminder() to update a certain reminder
    public void updateReminder(Reminder reminder) {
       String Query ="UPDATE "+TABLE_NAME+" SET "+COL_CONTENT +" = "+reminder.getContent() +", "+COL_IMPORTANT+" = "+String.valueOf(reminder.getImportant());
       Query = Query + " WHERE "+COL_ID+" = "+String.valueOf(reminder.getId());
       mDb.execSQL(Query);
    }
    //TODO implement the function deleteReminderById() to delete a certain reminder given its id
    public void deleteReminderById(int nId) {

        String Query ="DELETE FROM "+TABLE_NAME+" WHERE "+COL_ID+" = "+String.valueOf(nId);
        mDb.execSQL(Query);
    }

    //TODO implement the function deleteAllReminders() to delete all reminders
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
