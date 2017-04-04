package io.writter.ninaadpai.writter.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.writter.ninaadpai.writter.DashboardActivity;
import io.writter.ninaadpai.writter.R;
import io.writter.ninaadpai.writter.classes.Answer;
import io.writter.ninaadpai.writter.classes.Question;
import io.writter.ninaadpai.writter.classes.WritterUser;
import io.writter.ninaadpai.writter.fragments.FeedFragment;

/**
 * Created by ninaadpai on 3/24/17.
 */

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private static Context context;
    private List<Question> mDataSet;
    boolean liked, added;
    public FeedListAdapter(FragmentActivity f, List<Question> dataSet) {
        this.mDataSet = dataSet;
        this.context = f;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView postDetails;
        private TextView postQuestion;
        private TextView postDescription;
        private ImageView topPosterImage;
        private Button likeButton;
        private Button responseButton;
        private ImageView moreOptions;
        private TextView answerDetails;
        private ImageView addToNetwork;
        private int width;
        private AlertDialog.Builder addToNW;
         FirebaseAuth firebaseAuth;
         DatabaseReference databaseReference;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            postDetails = (TextView) v.findViewById(R.id.postDetails);
            postQuestion = (TextView) v.findViewById(R.id.postQuestion);
            topPosterImage = (ImageView) v.findViewById(R.id.posterPhoto);
            postDescription = (TextView) v.findViewById(R.id.postDesc);
            answerDetails = (TextView) v.findViewById(R.id.answerDetails);
            moreOptions = (ImageView) v.findViewById(R.id.moreOptions);
            addToNW = new AlertDialog.Builder(v.getContext());
            likeButton = (Button)v.findViewById(R.id.likeButton);
            responseButton = (Button)v.findViewById(R.id.responseButton);
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

    private String timeDiff(long timeDifferenceMilliseconds) {
        long diffSeconds = timeDifferenceMilliseconds / 1000;
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
        long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
        long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
        long diffYears = timeDifferenceMilliseconds / ((long)60 * 60 * 1000 * 24 * 365);

        if (diffSeconds < 1) {
            return "Less than a second ago";
        } else if (diffMinutes < 1) {
            return diffSeconds + " secs ago";
        } else if (diffHours < 1) {
            if(diffMinutes<2)
                return diffMinutes + " min ago";
            else
                return diffMinutes + " mins ago";
        } else if (diffDays < 1) {
            if(diffHours<2)
                return diffHours + " hr ago";
            else
                return diffHours + " hrs ago";
        } else if (diffWeeks < 1) {
            if(diffDays < 2)
                return diffDays + " day ago";
            else
                return diffDays + " days ago";
        } else if (diffMonths < 1) {
            return diffWeeks + " W ago";
        } else if (diffYears < 1) {
            return diffMonths + " M ago";
        } else {
            return diffYears + " yrs ago";
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
       final Question p = mDataSet.get(position);
        final long currentTime = System.currentTimeMillis();
        viewHolder.postDetails.setText("Question Answered For : "+p.getCategory()+"\t\t"+timeDiff(currentTime - Long.parseLong(String.valueOf(p.getUploadTime()))));
        viewHolder.postQuestion.setText(p.getQuestionText());
       // viewHolder.postDesc.setText("");
      //  viewHolder.postDesc.setVisibility(View.GONE);
        if(p.getLikers() != null)
           viewHolder.likeButton.setText("Like | "+String.valueOf(p.getLikers().size()));
        else
           viewHolder.likeButton.setText("Like | 0");
        viewHolder.likeButton.setTag("like");
        viewHolder.responseButton.setText("Respond | "+String.valueOf(p.getAnswers().size()));
        if(p.getAnswers().size() == 0) {
            viewHolder.postDescription.setText("No answers yet.");
            viewHolder.answerDetails.setText("This question has not been answered yet, be the first one to respond!");
        }

        else if(p.getAnswers().size() == 1) {
            DatabaseReference photoRef = FirebaseDatabase.getInstance().getReference().child(p.getAnswers().get(0).getProviderId().toString());
            Log.i("Photo ref", String.valueOf(photoRef));
            photoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String photoLink = dataSnapshot.child("profile_photo").child("encodedSchemeSpecificPart").getValue().toString();
                    Picasso.with(context)
                            .load("https:"+photoLink)
                            .rotate(0)
                            .transform(new FeedListAdapter.CircleTransform())
                            .into(viewHolder.topPosterImage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            viewHolder.postDescription.setText(p.getAnswers().get(0).getAnswerText().toString());
            viewHolder.answerDetails.setText(p.getAnswers().size() + " Answer, posted by " + p.getAnswers().get(0).getProviderName().toString() + "\t\t" + timeDiff(currentTime - Long.parseLong(p.getAnswers().get(0).getTimeStamp().toString())));
        }
        else {

            Collections.sort(p.getAnswers(), new Comparator<Answer>() {
                @Override
                public int compare(Answer o1, Answer o2) {
                    long time1 = Long.parseLong(String.valueOf(o1.getTimeStamp()));
                    long time2 = Long.parseLong(String.valueOf(o2.getTimeStamp()));
                    if(time1 < time2)
                        return 1;
                    else
                        return -1;
                }
            });

            DatabaseReference photoRef = FirebaseDatabase.getInstance().getReference().child(p.getAnswers().get(0).getProviderId().toString());
            Log.i("Photo ref", String.valueOf(photoRef));
            photoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        String photoLink = dataSnapshot.child("profile_photo").child("encodedSchemeSpecificPart").getValue().toString();
                        Picasso.with(context)
                                .load("https:"+photoLink)
                                .rotate(0)
                                .transform(new FeedListAdapter.CircleTransform())
                                .into(viewHolder.topPosterImage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            viewHolder.postDescription.setText(p.getAnswers().get(0).getAnswerText().toString());
            viewHolder.answerDetails.setText(p.getAnswers().size() + " Answers, top answer by "+p.getAnswers().get(0).getProviderName().toString()+"\t\t"+timeDiff(currentTime - Long.parseLong(p.getAnswers().get(0).getTimeStamp().toString())));
        }
        viewHolder.postQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

//       viewHolder.likedImage.setOnClickListener(new View.OnClickListener() {
//
//           @Override
//           public void onClick(View v) {
//               if(viewHolder.likedImage.getTag().toString().equals("like") ) {
//                   viewHolder.likedImage.postDelayed(new Runnable() {
//                       @Override
//                       public void run() {
//                           viewHolder.likedImage.animate()
//                                  // .alpha(0.0f)
//                                   .rotation(360)
//                                   .setDuration(500)
//                           .setListener(new AnimatorListenerAdapter() {
//                               @Override
//                               public void onAnimationEnd(Animator animation) {
//                                   super.onAnimationEnd(animation);
//                                   viewHolder.likedImage.setImageResource(R.drawable.liked);
//                                   viewHolder.likedImage.setTag("liked");
//                                 //  notifyItemChanged(position);
//                               }
//
//                           });
//                       }
//                   },0);
//                   liked = true;
//                   DashboardActivity.setLiked(liked);
//               }
//               else if(viewHolder.likedImage.getTag().toString().equals("liked") ) {
//                   viewHolder.likedImage.postDelayed(new Runnable() {
//                       @Override
//                       public void run() {
//                           viewHolder.likedImage.animate()
//                                   // .alpha(0.0f)
//                                   .rotation(-360)
//                                   .setDuration(500)
//                                   .setListener(new AnimatorListenerAdapter() {
//                                       @Override
//                                       public void onAnimationEnd(Animator animation) {
//                                           super.onAnimationEnd(animation);
//                                           viewHolder.likedImage.setImageResource(R.drawable.like);
//                                           viewHolder.likedImage.setTag("like");
//                                           //  notifyItemChanged(position);
//                                       }
//
//                                   });
//                       }
//                   },0);
//                   liked = false;
//                   DashboardActivity.setLiked(liked);
//               }
//           }
//       });
//
//        viewHolder.commentOnPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openAnswerPopUp(context, p.getCategory(), timeDiff(currentTime - Long.parseLong(String.valueOf(p.getUploadTime()))), p.getQuestionText(), "", p.getQuestionId());
//            }
//        });

    viewHolder.moreOptions.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
               LayoutInflater inflater = (LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
               final View dialogLayout = inflater.inflate(R.layout.answer_options_window, null);

               final android.app.AlertDialog dialog = builder.create();
               dialog.getWindow().setSoftInputMode(
                       WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
               dialog.setView(dialogLayout, 0, 0, 0, 0);
               dialog.setCanceledOnTouchOutside(true);
               dialog.setCancelable(true);
               WindowManager.LayoutParams wlmp = dialog.getWindow()
                       .getAttributes();
               wlmp.gravity = Gravity.CENTER;

               builder.setView(dialogLayout);
               dialog.show();

               dialog.findViewById(R.id.exitAnswerOptions).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                   }
               });

               dialog.findViewById(R.id.answerLink).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                       openAnswerPopUp(context, p.getCategory(), timeDiff(currentTime - Long.parseLong(String.valueOf(p.getUploadTime()))), p.getQuestionText(), "", p.getQuestionId());
                   }
               });

               dialog.findViewById(R.id.editTopic).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                       openTopicChangePopUp(context, p.getCategory(), timeDiff(currentTime - Long.parseLong(String.valueOf(p.getUploadTime()))), p.getQuestionText(), "", p.getQuestionId());
                   }
               });
           }
       });
        viewHolder.postQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        final List<Answer> answerProviders = new ArrayList<>();
//        for(int i=0; i<p.getAnswers().size(); i++) {
//            String providerId = p.getAnswers().get(i).getProviderId();
//            FirebaseDatabase.getInstance().getReference().child(providerId).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
//                        Answer answer = new Answer(ds.child("providerId").getValue().toString(), ds.child("providerName").getValue().toString(), ds.child("questionId").getValue().toString(), ds.child("answerText").getValue().toString(), ds.child("timeStamp").getValue(), (boolean) ds.child("anonymous").getValue(), null);
//                        answerProviders.add(answer);
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//            }
//        viewHolder.answerDetails.setText(p.getAnswers().size() + " Answers: Top Answer By, ");
    }

    private void openTopicChangePopUp(Context context, String category, String s, String questionText, String s1, String questionId) {
    }

    private void openAnswerPopUp(Context context, String topicString, String uploadedTime, String postQuestion, String postDesc, final String questionId) {
        Log.i("Topic String",topicString);
        AlertDialog.Builder answerBuilder = new AlertDialog.Builder(context);
        LayoutInflater answerInflater = (LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        final View answerdialogLayout = answerInflater.inflate(R.layout.answer_form, null);
        final AlertDialog answerDialog = answerBuilder.create();

        answerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        answerDialog.setView(answerdialogLayout, 0, 0, 0, 0);
        answerDialog.setCanceledOnTouchOutside(true);
        answerDialog.setCancelable(true);
        WindowManager.LayoutParams answerwlmp = answerDialog.getWindow().getAttributes();
        answerwlmp.gravity = Gravity.TOP;
        answerBuilder.setView(answerdialogLayout);
        answerDialog.show();
        final TextView topic = (TextView) answerDialog.findViewById(R.id.postingDetails1);
        final TextView question = (TextView) answerDialog.findViewById(R.id.questionText1);
        final EditText answerBox = (EditText) answerDialog.findViewById(R.id.answerBox);
        final CheckBox anonymousCB = (CheckBox) answerDialog.findViewById(R.id.anonymous);
        topic.setText(topicString+"\t\t"+uploadedTime);
        question.setText(postQuestion);
        answerDialog.findViewById(R.id.exit_answer_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerDialog.dismiss();
            }
        });

        answerDialog.findViewById(R.id.postAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(answerBox.getText().toString().isEmpty())) {
                    boolean anonymous;
                    if(anonymousCB.isChecked()) {
                        anonymous = true;
                    }
                    else
                        anonymous = false;
                    FeedFragment.postAnswer(questionId,answerBox.getText().toString().trim(), anonymous);
                    answerDialog.dismiss();
                }
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