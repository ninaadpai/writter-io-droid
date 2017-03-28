package io.writter.ninaadpai.writter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    Typeface domineBold;
    SearchResAdapter adapter;
    TextView searchTitle;
    ArrayList<String> topResults;
    ListView searchRes;
    boolean postWarning = false;
    boolean anonymous = false;
    CheckBox anonymousCheckbox;
    ImageView checkquestionImg;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Demo","SearchFragment onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchRes = (ListView) view.findViewById(R.id.searchResults);

        String[] values = new String[] {
                "What is the best time to visit California in terms of weather?",
                "Who won the most number of grand slams of tennis?",
                "Who won nobel peace prize in 2014?",
                "Who is the biggest earner in hollywood in 2016?",
                "Who is the biggest name in bollywood?",
                "I am a rookie in CS, which book do I use for Python?"
                ,"When is Pirates of the Caribbean 5th part releasing?",
                "Stomach Cancer Symptoms"
        };
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        searchTitle = (TextView)view.findViewById(R.id.searchFragTitle);

        topResults = getArguments().getStringArrayList("message");
        Log.i("Top Results Size:", String.valueOf(topResults.size()));
        if(topResults.size() > 0)
            searchTitle.setText("Your Search Matches");
        else if(topResults.size() == list.size()-1)
            searchTitle.setText("Don't see what you're searching for? Post it!");
        adapter = new SearchResAdapter(getActivity(),R.layout.search_res_item_row, topResults, domineBold);
        searchRes.setAdapter(adapter);
        domineBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Arimo-Bold.ttf");
        Button questionPost = (Button) view.findViewById(R.id.questionPostBtn);
        anonymousCheckbox = (CheckBox) view.findViewById(R.id.anonymousCheckbox);
        checkquestionImg = (ImageView) view.findViewById(R.id.checkquestionImg);
        checkquestionImg.setVisibility(View.INVISIBLE);
        questionPost.setTypeface(domineBold);
        anonymousCheckbox.setTypeface(domineBold);
        //Typeface arimoBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Arimo-Bold.ttf");
        searchTitle.setTypeface(domineBold);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Demo","SearchFragment onCreate");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Demo","SearchFragment onResume");

    }
    IQuestion mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Demo","SearchFragment onAttach");
        try{
            mListener = (IQuestion) activity;
        }catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()+" should implement IQuestion.");
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Demo","SearchFragment onActivityCreated");
        getActivity().findViewById(R.id.questionPostBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(postWarning == true ) {
                    mListener.startUpload();
                    if(anonymousCheckbox.isChecked()) {
                        anonymous = true;
                        mListener.postQuestion(anonymous);
                        mListener.doneUpload();
                        mListener.destroySearchFragment();
                    }
                    else {
                        anonymous = false;
                        mListener.postQuestion(anonymous);
                        mListener.doneUpload();
                        mListener.destroySearchFragment();
                    }
                }
                provideWarning();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Demo","SearchFragment onStart");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Demo","SearchFragment onDestroy");

    }

    private void provideWarning() {
        searchTitle.setText("Please confirm if the question is correct and press post again.");
        searchRes.setVisibility(View.INVISIBLE);
        checkquestionImg.setVisibility(View.VISIBLE);
        postWarning = true;
    }

    public interface onFragmentInteractionListener {
        public void moveToNextFragment();
    }

    static public interface IQuestion {
        void postQuestion(boolean anonymous);
        void startUpload();
        void doneUpload();
        void destroySearchFragment();
    }
}
