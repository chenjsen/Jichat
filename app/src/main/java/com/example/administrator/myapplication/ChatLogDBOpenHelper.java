package com.example.administrator.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class ChatLogDBOpenHelper extends SQLiteOpenHelper {
    public ChatLogDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE chat_log111(id INTEGER PRIMARY KEY AUTOINCREMENT,userAccount varchar(20),account varchar(20),content text,isLeft INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE chat_log222(id INTEGER PRIMARY KEY AUTOINCREMENT,userAccount varchar(20),account varchar(20),content text,isLeft INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE chat_log333(id INTEGER PRIMARY KEY AUTOINCREMENT,userAccount varchar(20),account varchar(20),content text,isLeft INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
