package com.cqrify.followme.dao;

import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Jocke Ådén on 26/10/2017.
 */

public class ContactsDAO {
    // const
    private ContactsDAO(){};

    // Table contents
    public static class ContactsEntry implements BaseColumns{
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME_LOOKUP_KEY = "lookup_key";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_NUMBER = "number";
        public static final String COLUMN_NAME_THUMBNAIL_URI = "thumbnail_uri";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + ContactsEntry.TABLE_NAME + " (" +
                                                    ContactsEntry._ID + " INTEGER PRIMARY KEY, " +
                                                    ContactsEntry.COLUMN_NAME_LOOKUP_KEY + " TEXT, " +
                                                    ContactsEntry.COLUMN_NAME_NAME + " TEXT, " +
                                                    ContactsEntry.COLUMN_NAME_NUMBER + " TEXT, " +
                                                    ContactsEntry.COLUMN_NAME_THUMBNAIL_URI + " TEXT)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS + " + ContactsEntry.TABLE_NAME;
}
