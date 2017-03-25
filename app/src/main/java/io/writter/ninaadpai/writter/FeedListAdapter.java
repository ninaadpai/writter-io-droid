package io.writter.ninaadpai.writter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by ninaadpai on 3/24/17.
 */

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<Post> mDataSet;
    private Typeface domineBold;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            textView = (TextView) v.findViewById(R.id.postDetails);
        }
    }
    public FeedListAdapter(List<Post> dataSet, Typeface domineBold) {
        mDataSet = dataSet;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.feed_recycler_row, viewGroup, false);

        return new ViewHolder(v);
    }

   @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
       final Post p = mDataSet.get(position);
        viewHolder.textView.setText(p.getUploadedBy()+" | "+p.getTopicString()+" | "+p.getUploadedTime());
        viewHolder.textView.setTypeface(domineBold);
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}