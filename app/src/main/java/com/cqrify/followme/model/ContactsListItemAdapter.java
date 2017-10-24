package com.cqrify.followme.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqrify.followme.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Jocke Ådén on 20/10/2017.
 */

public class ContactsListItemAdapter  extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Contact> contacts;

    private class ViewHolder {
        TextView name;
        TextView number;
    }

    public ContactsListItemAdapter(Context context, ArrayList<Contact> contacts){
        inflater = LayoutInflater.from(context);
        this.contacts = contacts;
    }

    public int getCount(){
        return contacts.size();
    }

    public Contact getItem(int position){
        return contacts.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_contact_list, null);
            holder.name = (TextView) convertView.findViewById(R.id.text1); // TODO better var names
            holder.number = (TextView) convertView.findViewById(R.id.text2);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(contacts.get(position).getName());
        holder.number.setText(contacts.get(position).getName());
        return convertView;
    }
}