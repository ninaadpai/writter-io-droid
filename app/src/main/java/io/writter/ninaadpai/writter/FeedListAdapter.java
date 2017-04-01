package io.writter.ninaadpai.writter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ninaadpai on 3/24/17.
 */

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private static Context context;
    private List<Post> mDataSet;
    boolean liked, added;

    public FeedListAdapter(FragmentActivity f, List<Post> dataSet) {
        this.mDataSet = dataSet;
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
        private ImageView moreOptions;
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
            profileImage = (ImageView)v.findViewById(R.id.profileImage);
            postDetails = (TextView) v.findViewById(R.id.postDetails);
            postQuestion = (TextView) v.findViewById(R.id.postQuestion);
            postDesc = (TextView) v.findViewById(R.id.postDesc);
            likedImage = (ImageView) v.findViewById(R.id.likeDetails);
            likeCount = (TextView) v.findViewById(R.id.likeCount);
            commentCount = (TextView) v.findViewById(R.id.commentCount);
            moreOptions = (ImageView) v.findViewById(R.id.moreOptions);
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
        if(!p.getImgUrl().equals(null)){
            Picasso.with(context)
                    .load(p.getImgUrl())
                    .rotate(0)
                    .transform(new FeedListAdapter.CircleTransform())
                    .into(viewHolder.profileImage);
        }
        viewHolder.postQuestion.setText(p.getPostQuestion());
       viewHolder.postDesc.setText(p.getPostDesc());
       viewHolder.likeCount.setText("27.2k");
       viewHolder.commentCount.setText("5.5k");
       viewHolder.likedImage.setTag("like");
//       viewHolder.addToNetwork.setTag("add");
//        viewHolder.addToNetwork.setVisibility(View.INVISIBLE);
        viewHolder.postQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layoutValue = LayoutInflater.from(context).inflate(R.layout.request_sent, null);
                Toast toast = new Toast(context);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 200);
                toast.setView(layoutValue);//setting the view of custom toast layout
                toast.show();
            }
        });
       viewHolder.likedImage.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               if(viewHolder.likedImage.getTag().toString().equals("like") ) {
                   viewHolder.likedImage.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           viewHolder.likedImage.animate()
                                  // .alpha(0.0f)
                                   .rotation(360)
                                   .setDuration(500)
                           .setListener(new AnimatorListenerAdapter() {
                               @Override
                               public void onAnimationEnd(Animator animation) {
                                   super.onAnimationEnd(animation);
                                   viewHolder.likedImage.setImageResource(R.drawable.liked);
                                   viewHolder.likedImage.setTag("liked");
                                 //  notifyItemChanged(position);
                               }

                           });
                       }
                   },0);
                   liked = true;
                   DashboardActivity.setLiked(liked);
               }
               else if(viewHolder.likedImage.getTag().toString().equals("liked") ) {
                   viewHolder.likedImage.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           viewHolder.likedImage.animate()
                                   // .alpha(0.0f)
                                   .rotation(-360)
                                   .setDuration(500)
                                   .setListener(new AnimatorListenerAdapter() {
                                       @Override
                                       public void onAnimationEnd(Animator animation) {
                                           super.onAnimationEnd(animation);
                                           viewHolder.likedImage.setImageResource(R.drawable.like);
                                           viewHolder.likedImage.setTag("like");
                                           //  notifyItemChanged(position);
                                       }

                                   });
                       }
                   },0);
                   liked = false;
                   DashboardActivity.setLiked(liked);
               }
           }
       });
//       if(p.getUploadedBy().equals("Posted as Anonymous") || p.getUploadedBy().equals("Posted by you")){
//           viewHolder.addToNetwork.setVisibility(View.INVISIBLE);
//       }
//       final Dialog dialog = new Dialog(context);
//       dialog.setContentView(R.layout.custom_alert_dialog);
//       TextView text = (TextView)dialog.findViewById(R.id.dialogInstr);
//       Button yesBtn = (Button) dialog.findViewById(R.id.yesBtn);
//       Button noBtn = (Button) dialog.findViewById(R.id.noBtn);
//       viewHolder.addToNetwork.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               if (viewHolder.addToNetwork.getTag().toString().equals("add")) {
//                   viewHolder.addToNW.setTitle("Follow " + p.getUploadedBy())
//                           .setMessage("Are you sure to want to send a follow request?")
//                           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                               @Override
//                               public void onClick(DialogInterface dialog, int which) {
//                                   dialog.dismiss();
//                                   viewHolder.addToNetwork.postDelayed(new Runnable() {
//                                       @Override
//                                       public void run() {
//                                           viewHolder.addToNetwork.animate()
//                                                   //.alpha(0.0f)
//                                                   .rotation(360)
//                                                   .setDuration(500);
//                                       }
//                                   }, 0);
//                                   viewHolder.addToNetwork.setImageResource(R.drawable.added);
//                                   View layoutValue = LayoutInflater.from(context).inflate(R.layout.request_sent, null);
//                                   Toast toast = new Toast(context);
//                                   toast.setDuration(Toast.LENGTH_LONG);
//                                   toast.setGravity(Gravity.BOTTOM, 0, 200);
//                                   toast.setView(layoutValue);//setting the view of custom toast layout
//                                   toast.show();
//                                   viewHolder.addToNetwork.setTag("added");
//                               }
//                           }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialog, int which) {
//                           dialog.dismiss();
//                       }
//                   }).setCancelable(false);
//                   AlertDialog alert = viewHolder.addToNW.create();
//                   alert.show();
//               }
//
//               else if (viewHolder.addToNetwork.getTag().toString().equals("added")) {
//                   viewHolder.addToNW.setTitle("Remove request to " + p.getUploadedBy())
//                           .setMessage("Are you sure to want to remove follow request?")
//                           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                               @Override
//                               public void onClick(DialogInterface dialog, int which) {
//                                   dialog.dismiss();
//                                   viewHolder.addToNetwork.postDelayed(new Runnable() {
//                                       @Override
//                                       public void run() {
//                                           viewHolder.addToNetwork.animate()
//                                                   //.alpha(0.0f)
//                                                   .rotation(-360)
//                                                   .setDuration(500);
//                                       }
//                                   }, 0);
//                                   viewHolder.addToNetwork.setImageResource(R.drawable.add);
//                                   View layoutValue = LayoutInflater.from(context).inflate(R.layout.request_cancelled, null);
//                                   Toast toast = new Toast(context);
//                                   toast.setDuration(Toast.LENGTH_LONG);
//                                   toast.setGravity(Gravity.BOTTOM, 0, 200);
//                                   toast.setView(layoutValue);//setting the view of custom toast layout
//                                   toast.show();
//                                   viewHolder.addToNetwork.setTag("add");
//                               }
//                           }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialog, int which) {
//                           dialog.dismiss();
//                       }
//                   }).setCancelable(false);
//                   AlertDialog alert = viewHolder.addToNW.create();
//                   alert.show();
//               }
//           }
//       });
       viewHolder.moreOptions.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
               LayoutInflater inflater = (LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
               final View dialogLayout = inflater.inflate(R.layout.answer_options_window,
                       null);

               final android.app.AlertDialog dialog = builder.create();
               dialog.getWindow().setSoftInputMode(
                       WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
               dialog.setView(dialogLayout, 0, 0, 0, 0);
               dialog.setCanceledOnTouchOutside(true);
               dialog.setCancelable(true);
               WindowManager.LayoutParams wlmp = dialog.getWindow()
                       .getAttributes();
               wlmp.gravity = Gravity.BOTTOM;

               builder.setView(dialogLayout);
               dialog.show();

               dialog.findViewById(R.id.exitAnswerOptions).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                   }
               });
           }
       });
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}