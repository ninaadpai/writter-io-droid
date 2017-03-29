package io.writter.ninaadpai.writter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ninaadpai on 3/24/17.
 */

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private static Context context;
    private List<Post> mDataSet;
    private Typeface domineBold;
    private Typeface share;

    public FeedListAdapter(FragmentActivity f, List<Post> dataSet, Typeface domineBold, Typeface share) {
        this.mDataSet = dataSet;
        this.domineBold = domineBold;
        this.share = share;
        this.context = f;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView postDetails;
        private TextView postQuestion;
        private TextView postDesc;
        private ImageView likedImage;
        private TextView likeCount;
        private TextView commentCount;
        private ImageView addToNetwork;
        private int width;
        private AlertDialog.Builder addToNW;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            postDetails = (TextView) v.findViewById(R.id.postDetails);
            postQuestion = (TextView) v.findViewById(R.id.postQuestion);
            postDesc = (TextView) v.findViewById(R.id.postDesc);
            likedImage = (ImageView) v.findViewById(R.id.likeDetails);
            likeCount = (TextView) v.findViewById(R.id.likeCount);
            commentCount = (TextView) v.findViewById(R.id.commentCount);
            addToNetwork = (ImageView) v.findViewById(R.id.addUser);
            addToNW = new AlertDialog.Builder(v.getContext());
            WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            width = window.getDefaultDisplay().getWidth();
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
       viewHolder.likeCount.setTypeface(domineBold);
       viewHolder.likeCount.setText("27.2k");
       viewHolder.commentCount.setTypeface(domineBold);
       viewHolder.commentCount.setText("5.5k");
       viewHolder.likedImage.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               viewHolder.likedImage.setImageResource(R.drawable.liked);
           }
       });
       viewHolder.addToNetwork.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.i("Clicked", "Add");
                viewHolder.addToNW.setTitle("Send a request")
                .setMessage("Are you sure to want to send a follow request?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                viewHolder.addToNetwork.setImageResource(R.drawable.added);
                                viewHolder.addToNetwork.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        viewHolder.addToNetwork.animate()
                                                .alpha(0.0f)
                                                .rotation(45)
                                                .setDuration(1500)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        super.onAnimationEnd(animation);
                                                        viewHolder.addToNetwork.setVisibility(View.GONE);
                                                        notifyItemChanged(position);
                                                    }

                                                });
                                    }
                                },5000);
                          }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false);
               AlertDialog alert = viewHolder.addToNW.create();
               alert.show();
           }
       });
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}