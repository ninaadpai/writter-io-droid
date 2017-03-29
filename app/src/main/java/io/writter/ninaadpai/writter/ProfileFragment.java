package io.writter.ninaadpai.writter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Demo","ProfileFragment onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Demo","ProfileFragment onCreate");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Demo","ProfileFragment onResume");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Demo","ProfileFragment onAttach");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Demo","ProfileFragment onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Demo","ProfileFragment onStart");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Demo","ProfileFragment onDestroy");

    }

    public interface onFragmentInteractionListener {
        public void moveToNextFragment();
    }

}
