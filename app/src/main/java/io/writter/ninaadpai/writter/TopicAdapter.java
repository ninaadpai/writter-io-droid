package io.writter.ninaadpai.writter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ninaadpai on 3/23/17.
 */

public class TopicAdapter extends ArrayAdapter<String> {
    List<String> mData;
    Context mContext;
    int mResource;
    Typeface domineBold;

    public TopicAdapter(Context context, int resource, List<String> objects, Typeface domineBold) {
        super(context, resource, objects);
        this.mData = objects;
        this.mContext = context;
        this.mResource = resource;
        this.domineBold = domineBold;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        String p = mData.get(position);
        TextView text = (TextView)convertView.findViewById(R.id.textView2);
        text.setTypeface(domineBold);
        text.setText(p);
        return convertView;
    }
}