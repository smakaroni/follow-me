package com.cqrify.followme.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.cqrify.followme.dao.ContactsDAO.SQL_CREATE_ENTRIES;

/**
 * Created by Jocke Ådén on 26/10/2017.
 */

public class ContactsDbHelper extends SQLiteOpenHelper {
    // If database schema is changed, we must increment database version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ContactsDAO";

    public ContactsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void onDownGrade(SQLiteDatabase db, int oldVersion, int newVersion){}

}
