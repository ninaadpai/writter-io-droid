package io.writter.ninaadpai.writter.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninaadpai on 4/1/17.
 */

public class ExampleDateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<String> options = new ArrayList<>();

    public ExampleDateAdapter() {
            String[] opts = {"Asked | 10","Answered | 32","Followers | 210","Following | 194","Topics | 90","Blog | 3","Podcasts | 5"};
            for(String opt : opts) {
                options.add(opt);
            }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExampleDateReyclerViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ExampleDateReyclerViewHolder)holder).setDate(options.get(position));
    }

    @Override
    public int getItemCount() {
        return options.size();
    }
}