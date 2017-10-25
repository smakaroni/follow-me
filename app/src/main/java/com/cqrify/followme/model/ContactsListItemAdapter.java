package com.cqrify.followme.model;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqrify.followme.R;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.width;
import static android.support.design.R.attr.height;

/**
 * Created by Jocke Ådén on 20/10/2017.
 */

public class ContactsListItemAdapter extends ArrayAdapter<Contact> {

    private static final String LOG_TAG = ContactsListItemAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private ArrayList<Contact> contacts;
    Context mContext;

    private class ViewHolder {
        TextView name;
        TextView number;
        ImageView imageView;
    }

    public ContactsListItemAdapter(Context context, ArrayList<Contact> contacts){
        super(context, R.layout.contacts_list_item, contacts);
        this.contacts = contacts;
        this.mContext = context;
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

    private int lastPosition = -1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        ViewHolder viewHolder;

        final View result;


        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contacts_list_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.list_image);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;


        viewHolder.name.setText(contact.getName());
        viewHolder.number.setText(contact.getNumber());
        if(contact.getThumbNailUri() != null){
            Log.d(LOG_TAG, "Contact got image: " + contact.getThumbNailUri());
            Uri imageUri = Uri.parse(contact.getThumbNailUri());
            viewHolder.imageView.setImageURI(imageUri);
        }else{

        }
        return convertView;
    }

}