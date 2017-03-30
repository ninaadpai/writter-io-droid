package io.writter.ninaadpai.writter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
public class FeedFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    List<Post> posts = new ArrayList<>();
    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Demo","FeedFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        final Typeface domineBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FiraSansCondensed-Regular.ttf");
        final Typeface share = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Share-Bold.ttf");
        final Typeface arvoBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/CrimsonText-Semibold.ttf");
        final FragmentActivity f = getActivity();
        final RecyclerView feedRecycler = (RecyclerView)view.findViewById(R.id.feedRecycler);
        LinearLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 1);
        feedRecycler.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("questions_pool");
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String currentUid = currentUser.getUid();
        Log.i("Current User",currentUid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dS : dataSnapshot.getChildren()) {
                    String time = String.valueOf(dS.child("uploadTime").getValue());
                    long milliSeconds = Long.parseLong(time);
                    boolean anonymous = (boolean) dS.child("anonymous").getValue();
                    long currentTime = System.currentTimeMillis();
                    String poster = null;
                    if(currentUid.equals(dS.child("userId").getValue())) {
                        poster = "Posted by you";
                    }
                    else if(!(currentUid.equals(dS.child("userId").getValue())) && anonymous == false) {
                        poster = String.valueOf(dS.child("userName").getValue());
                    }
                    else if(!(currentUid.equals(dS.child("userId").getValue())) && anonymous == true) {
                        poster = "Posted as Anonymous";
                    }
                    Log.i("Posted user",String.valueOf(dS.child("userId").getValue()));
                    String timeAsString = timeDiff(currentTime-milliSeconds);
                     Post p = new Post("",
                             poster,
                             String.valueOf(dS.child("category").getValue()),
                             timeAsString,
                             String.valueOf(dS.child("questionText").getValue()),
                            "");
                    posts.add(p);

                }
                final FeedListAdapter feedListAdapter = new FeedListAdapter(f, posts, domineBold, arvoBold);
                feedRecycler.setAdapter(feedListAdapter);
                feedRecycler.setHasFixedSize(true);
                feedRecycler.setItemAnimator(new SlideInUpAnimator());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
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
            return "less than a second ago";
        } else if (diffMinutes < 1) {
            return diffSeconds + " secs ago";
        } else if (diffHours < 1) {
            return diffMinutes + " mins ago";
        } else if (diffDays < 1) {
            return diffHours + " hrs ago";
        } else if (diffWeeks < 1) {
            return diffDays + " D ago";
        } else if (diffMonths < 1) {
            return diffWeeks + " W ago";
        } else if (diffYears < 1) {
            return diffMonths + " M ago";
        } else {
            return diffYears + " yrs ago";
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Demo","FeedFragment onCreate");

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        Log.i("Demo","FeedFragment onResume");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Demo","FeedFragment onAttach");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Demo","FeedFragment onActivityCreated");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        Log.i("Demo","FeedFragment onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Demo","FeedFragment onDestroy");

    }

    public interface onFragmentInteractionListener {
        public void moveToNextFragment();
    }
}
