package io.writter.ninaadpai.writter;

import android.content.Context;
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
    List<String> searchResults;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Demo","SearchFragment onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ListView searchRes = (ListView) view.findViewById(R.id.searchResults);

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
        TextView searchTitle = (TextView)view.findViewById(R.id.searchFragTitle);

        ArrayList<String> topResults = getArguments().getStringArrayList("message");
        Log.i("Top Results Size:", String.valueOf(topResults.size()));
        if(topResults.size() > 0)
            searchTitle.setText("YOUR SEARCH MATCHES");
        else if(topResults.size() <= 1)
            searchTitle.setText("DON'T SEE WHAT YOU'RE SEARCHING FOR? POST IT!");
        adapter = new SearchResAdapter(getActivity(),R.layout.search_res_item_row, topResults, domineBold);
        searchRes.setAdapter(adapter);
        domineBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoSlab-Regular.ttf");
        Button questionPost = (Button) view.findViewById(R.id.questionPostBtn);
        CheckBox anonymousCheckbox = (CheckBox) view.findViewById(R.id.anonymousCheckbox);
        questionPost.setTypeface(domineBold);
        anonymousCheckbox.setTypeface(domineBold);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Demo","SearchFragment onAttach");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Demo","SearchFragment onActivityCreated");

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

    public interface onFragmentInteractionListener {
        public void moveToNextFragment();
    }
}
