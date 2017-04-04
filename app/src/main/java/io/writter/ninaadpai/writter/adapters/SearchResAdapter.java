package io.writter.ninaadpai.writter.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.writter.ninaadpai.writter.R;

/**
 * Created by ninaadpai on 3/26/17.
 */

public class SearchResAdapter extends ArrayAdapter<String> {
    List<String> mData;
    Context mContext;
    int mResource;

    public SearchResAdapter(Context context, int search_res_item_row, List<String> list) {
        super(context, search_res_item_row, list);
        //super(context, list);
        this.mContext = context;
        this.mData = list;
        this.mResource = search_res_item_row;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        String p = mData.get(position);
        TextView text = (TextView)convertView.findViewById(R.id.search_res);

        text.setText(p);
        return convertView;
    }
}
