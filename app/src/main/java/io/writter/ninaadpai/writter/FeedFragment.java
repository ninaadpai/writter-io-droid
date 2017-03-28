package io.writter.ninaadpai.writter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
public class FeedFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    public static List<Post> posts = new ArrayList<>();
    Post p;
    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Demo","FeedFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        final Typeface domineBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoSlab-Regular.ttf");
        final Typeface share = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Share-Bold.ttf");
        final FragmentActivity f = getActivity();
        final RecyclerView feedRecycler = (RecyclerView)view.findViewById(R.id.feedRecycler);
        LinearLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 1);
        feedRecycler.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("questions_pool");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dS : dataSnapshot.getChildren()) {
                   // Log.i("Questions", String.valueOf(dS.getValue()));
                     p = new Post("",
                            String.valueOf(dS.child("userId").getValue()),
                             String.valueOf(dS.child("category").getValue()),
                             String.valueOf(dS.child("uploadTime").getValue()),
                             String.valueOf(dS.child("questionText").getValue()),
                            "");
             //       Log.i("Post", String.valueOf(p));
                    posts.add(p);
                }
                final FeedListAdapter feedListAdapter = new FeedListAdapter(f, posts, domineBold, share);
                feedRecycler.setAdapter(feedListAdapter);
                feedRecycler.setHasFixedSize(true);
                feedRecycler.setItemAnimator(new SlideInUpAnimator());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      //  Log.i("Posts",posts.toString());
        return view;
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
