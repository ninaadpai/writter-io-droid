package io.writter.ninaadpai.writter.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.writter.ninaadpai.writter.R;
import io.writter.ninaadpai.writter.adapters.SearchResAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SearchResAdapter adapter;
    TextView searchTitle;
    ArrayList<String> topResults;
    ListView searchRes;
    static boolean postWarning = false;
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
        adapter = new SearchResAdapter(getActivity(),R.layout.search_res_item_row, topResults);
        searchRes.setAdapter(adapter);
        anonymousCheckbox = (CheckBox) view.findViewById(R.id.anonymousCheckbox);
        checkquestionImg = (ImageView) view.findViewById(R.id.checkquestionImg);
        checkquestionImg.setVisibility(View.INVISIBLE);
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
                    if(anonymousCheckbox.isChecked()) {
                        anonymous = true;
                    }
                    else {
                        anonymous = false;
                    }
                    mListener.startUpload();
                    mListener.postQuestion(anonymous);
                    mListener.doneUpload();
                    try {
                        mListener.destroySearchFragment();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }
                }
                if(postWarning == false) {
                    provideWarning();
                    mListener.sanitizeQuestionText();
                    postWarning = true;
                }
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
        searchTitle.setText("Please form your question as a sentence and press post again.");
        searchRes.setVisibility(View.INVISIBLE);
        checkquestionImg.setVisibility(View.VISIBLE);
    }

    static public interface IQuestion {
        void postQuestion(boolean anonymous);
        void startUpload();
        void doneUpload();
        void destroySearchFragment() throws IllegalAccessException, java.lang.InstantiationException;
        void sanitizeQuestionText();

    }
}
