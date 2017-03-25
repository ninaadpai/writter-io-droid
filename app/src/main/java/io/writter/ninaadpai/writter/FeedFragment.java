package io.writter.ninaadpai.writter;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Demo","FeedFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        final Typeface domineBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoSlab-Regular.ttf");
        EditText searchFeed = (EditText)view.findViewById(R.id.searchFeed);
        searchFeed.setTypeface(domineBold);
        final FragmentActivity f = getActivity();
        final RecyclerView feedRecycler = (RecyclerView)view.findViewById(R.id.feedRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        feedRecycler.setLayoutManager(layoutManager);
        final List<Post> posts = new ArrayList<>();
        posts.add(new Post("","Ninaad Pai","Workout","6 hrs","What kind of protein is suitable for Ectomorph?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));
        posts.add(new Post("","Ninaad Pai","Workout","6 hrs","What kind of protein is suitable for Ectomorph?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));
        posts.add(new Post("","Ninaad Pai","Workout","6 hrs","What kind of protein is suitable for Ectomorph?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));
        posts.add(new Post("","Ninaad Pai","Workout","6 hrs","What kind of protein is suitable for Ectomorph?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));
        posts.add(new Post("","Ninaad Pai","Workout","6 hrs","What kind of protein is suitable for Ectomorph?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));
        posts.add(new Post("","Ninaad Pai","Workout","6 hrs","What kind of protein is suitable for Ectomorph?","Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah"));

        final FeedListAdapter feedListAdapter = new FeedListAdapter(posts, domineBold);
        feedRecycler.setAdapter(feedListAdapter);
        feedRecycler.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Demo","FeedFragment onCreate");

    }

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
