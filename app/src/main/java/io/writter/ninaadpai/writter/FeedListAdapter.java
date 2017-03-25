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
    public FeedListAdapter(List<Post> dataSet, Typeface domineBold) {
        this.mDataSet = dataSet;
        this.domineBold = domineBold;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView postDetails;
        private TextView postQuestion;
        private TextView postDesc;
        private ImageView likedImage;
        boolean likedBool = false;
        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            postDetails = (TextView) v.findViewById(R.id.postDetails);
            postQuestion = (TextView) v.findViewById(R.id.postQuestion);
            postDesc = (TextView) v.findViewById(R.id.postDesc);
            likedImage = (ImageView) v.findViewById(R.id.likeDetails);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.feed_recycler_row, viewGroup, false);
        v.setClickable(true);
        v.setFocusableInTouchMode(true);
        return new ViewHolder(v);
    }

   @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
       final Post p = mDataSet.get(position);
        viewHolder.postDetails.setText(p.getUploadedBy()+" \t\t "+p.getTopicString()+" \t\t "+p.getUploadedTime());
        viewHolder.postDetails.setTypeface(domineBold);
        viewHolder.postQuestion.setText(p.getPostQuestion());
        viewHolder.postQuestion.setTypeface(domineBold);
       viewHolder.postDesc.setText(p.getPostDesc());
       viewHolder.postDesc.setTypeface(domineBold);

       viewHolder.likedImage.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               if(viewHolder.likedImage.getDrawable().getConstantState() == v.getResources().getDrawable(R.drawable.like).getConstantState()){
                   viewHolder.likedImage.setImageResource(R.drawable.liked);
             //      notifyItemChanged(position);
               }
               if(viewHolder.likedImage.getDrawable().getConstantState() == v.getResources().getDrawable(R.drawable.liked).getConstantState()){
                   viewHolder.likedImage.setImageResource(R.drawable.like);
              //     notifyItemChanged(position);
               }

           }
       });
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}