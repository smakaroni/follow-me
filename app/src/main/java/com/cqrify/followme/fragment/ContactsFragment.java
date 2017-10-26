package com.cqrify.followme.fragment;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cqrify.followme.R;
import com.cqrify.followme.dao.ContactsDAO;
import com.cqrify.followme.dao.helper.ContactsDbHelper;
import com.cqrify.followme.model.Contact;
import com.cqrify.followme.model.ContactsListItemAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jocke Ådén on 20/10/2017.
 */

// search contacts / show contacts view
public class ContactsFragment extends Fragment{

    private static final String LOG_TAG = ContactsFragment.class.getSimpleName();

    private ListView listview;
    SimpleCursorAdapter adapter;

    private String[] PROJECTION = {
            ContactsDAO.ContactsEntry._ID,
            ContactsDAO.ContactsEntry.COLUMN_NAME_LOOKUP_KEY,
            ContactsDAO.ContactsEntry.COLUMN_NAME_NAME,
            ContactsDAO.ContactsEntry.COLUMN_NAME_NUMBER,
            ContactsDAO.ContactsEntry.COLUMN_NAME_THUMBNAIL_URI,
    };

    ContactsDbHelper mDbHelper;

    public static ContactsFragment newInstance(){
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mDbHelper = new ContactsDbHelper(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ArrayList<Contact> contacts = getContactList();

        for(Iterator<Contact> iterator = contacts.iterator(); iterator.hasNext();){
            Contact contact = iterator.next();
            Log.d(LOG_TAG, "Item name: " + contact.getName());
        }

        renderAddedContactsList(contacts);

        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    // TODO Implement a delete contacts method
    private void deleteContacts(){
        // Empties all the contacts
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(ContactsDAO.ContactsEntry.TABLE_NAME, null, null);
    }

    private ArrayList<Contact> getContactList(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // db.delete(ContactsDAO.ContactsEntry.TABLE_NAME, null, null);

        // We select nothing here since we want to display all the contacts in the db
        Cursor cursor = db.query(
                ContactsDAO.ContactsEntry.TABLE_NAME,
                PROJECTION,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Contact> contacts = new ArrayList();
        while(cursor.moveToNext()){
            Contact contact = new Contact(
                    cursor.getLong(cursor.getColumnIndexOrThrow(ContactsDAO.ContactsEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsDAO.ContactsEntry.COLUMN_NAME_LOOKUP_KEY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsDAO.ContactsEntry.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsDAO.ContactsEntry.COLUMN_NAME_NUMBER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsDAO.ContactsEntry.COLUMN_NAME_THUMBNAIL_URI))
            );
            contacts.add(contact);
        }
        cursor.close();

        return contacts;
    }

    private void renderAddedContactsList(ArrayList<Contact> contacts){
        listview = (ListView) getActivity().findViewById(R.id.added_contact_list);
    }

}
