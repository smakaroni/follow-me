package com.cqrify.followme.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
    private SimpleCursorAdapter mCursorAdapter;
    //private ContactsListItemAdapter mCursorAdapter;

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

        // Gets the ListView from the View list of the parent activity
        //mContactsList = (ListView) getActivity().findViewById(R.id.contacts_list_view);
        mContactsList = (ListView) getActivity().findViewById(R.id.contact_list);

        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contacts_list_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0
        );

        getLoaderManager().initLoader(0, null, this);

        ArrayList<Contact> contacts = new ArrayList<Contact>();

       // mCursorAdapter = new ContactsListItemAdapter(getActivity().getApplicationContext(), contacts);

        mContactsList.setAdapter(mCursorAdapter);

        mContactsList.setOnItemClickListener(this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        mSelectionArgs[0] = "%" + "" + "%"; // "" is future searchstring
        Log.d(LOG_TAG, mSelectionArgs[0]);
        // start query
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
         mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(
            AdapterView<?> parent, View item, int position, long rowID) {
        Log.d(LOG_TAG, "item click.. pos: " + position);
    }
}