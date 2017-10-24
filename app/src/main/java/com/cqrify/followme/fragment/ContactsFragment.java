package com.cqrify.followme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqrify.followme.R;

/**
 * Created by Jocke Ådén on 20/10/2017.
 */

public class ContactsFragment extends Fragment{

    public static ContactsFragment newInstance(){
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

}
