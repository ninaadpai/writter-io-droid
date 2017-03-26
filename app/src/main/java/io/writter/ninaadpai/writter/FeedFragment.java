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

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
public class FeedFragment extends Fragment {
    EditText searchFeed;
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
        final List<Post> posts = new ArrayList<>();
        posts.add(new Post("","Ninaad Pai","Workout","6 hrs","What kind of protein is suitable for Ectomorph? I would like to know a bit better about diet tips for fat loss.".replace("?","?\n"),"Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));
        posts.add(new Post("","Ojasvini Bali","Travel","23 mins","Best time to travel to Indonesia?","I wanted to travel to Indonesia this year, what are the best places to see around there?"));
        posts.add(new Post("","Ketan Joshi","Health","2 hrs","Caloric deficit diet?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));
        posts.add(new Post("","Neeraj Pai","Workout","6 hrs","What kind of protein is suitable for Ectomorph?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));
        posts.add(new Post("","Akshay Sathe","Workout","6 hrs","What kind of protein is suitable for Ectomorph?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));
        posts.add(new Post("","Amey Kelkar","Workout","6 hrs","What kind of protein is suitable for Ectomorph?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));

        final FeedListAdapter feedListAdapter = new FeedListAdapter(f, posts, domineBold, share);
        feedRecycler.setAdapter(feedListAdapter);
        feedRecycler.setHasFixedSize(true);
        feedRecycler.setItemAnimator(new SlideInUpAnimator());
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
