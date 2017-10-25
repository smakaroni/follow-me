package com.cqrify.followme.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cqrify.followme.R;
import com.cqrify.followme.model.Contact;
import com.cqrify.followme.model.ContactsListItemAdapter;

import java.util.ArrayList;

import static android.R.id.list;

/**
 * Created by Jocke Ådén on 20/10/2017.
 */

public class ContactListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private static final String LOG_TAG = ContactListFragment.class.getSimpleName();

    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME
    };

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
    };

    private static final int CONTACT_ID_INDEX = 0;
    private static final int LOOKUP_KEY_INDEX = 1;

    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

    private String mSearchString;
    private String[] mSelectionArgs = {mSearchString};

    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    ListView mContactsList;
    long mContactId;
    String mContactKey;
    Uri mContactUri;
    // private SimpleCursorAdapter mCursorAdapter;
    private ContactsListItemAdapter mCursorAdapter;

    public static ContactListFragment newInstance(){
        ContactListFragment fragment = new ContactListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mContactsList = (ListView) getActivity().findViewById(R.id.contact_list);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        mSelectionArgs[0] = "%" + "" + "%"; // "" is future searchstring
        Log.d(LOG_TAG, mSelectionArgs[0]);

        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        ArrayList<Contact> contacts = new ArrayList<Contact>();

        while(data.moveToNext()){

            ContentResolver cr = getContext().getContentResolver();
            String id = data.getString(data.getColumnIndex(PROJECTION[0]));

            String number = "";
            int numbersAmount = 0;

            if (Integer.parseInt(data.getString(
                    data.getColumnIndex(PROJECTION[3]))) > 0) {
                Cursor pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                        new String[]{id}, null);

                while (pCur.moveToNext()) {
                    numbersAmount++;
                    number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    int type = pCur.getInt(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    switch(type){ // TODO handle multiple numbers
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            break;
                    }
                }
                pCur.close();
            }

            // TODO Implement
            if(numbersAmount < 1){
                number = getString(R.string.no_numbers_found);
            }else if(numbersAmount > 1){
                number = getString(R.string.several_numbers_found);
            }

            Contact contact = new Contact(id,
                    data.getString(data.getColumnIndex(PROJECTION[1])),
                    data.getString(data.getColumnIndex(PROJECTION[2])),
                    number,
                    data.getString(data.getColumnIndex(PROJECTION[4])));

            contacts.add(contact);

        }

        mCursorAdapter = new ContactsListItemAdapter(getActivity().getApplicationContext(), contacts);
        mContactsList.setAdapter(mCursorAdapter);
        mContactsList.setOnItemClickListener(this);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
         // mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(
            AdapterView<?> parent, View item, int position, long rowID) {
        Contact contact = (Contact) parent.getItemAtPosition(position);
        // TODO if contact has more than 1 number, we need to let them choose which number to use

        Toast.makeText(getActivity().getApplicationContext(), contact.toString(), Toast.LENGTH_LONG).show();

        Log.d(LOG_TAG, contact.toString());
    }
}