package io.writter.ninaadpai.writter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ninaadpai on 4/1/17.
 */

class ExampleDateReyclerViewHolder extends RecyclerView.ViewHolder {

    public ExampleDateReyclerViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.example_date_viewholder, parent, false));
    }

    public void setDate(String opt) {
        ((TextView)(itemView.findViewById(R.id.optView))).setText(opt);
    }
}
