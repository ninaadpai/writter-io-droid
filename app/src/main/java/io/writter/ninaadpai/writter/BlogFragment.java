package io.writter.ninaadpai.writter;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment {


    public BlogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Demo","BlogFragment onCreateView");
        View view1 =  inflater.inflate(R.layout.fragment_blog, container, false);
        Typeface domineBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Arimo-Bold.ttf");
        TextView noBlog = (TextView)view1.findViewById(R.id.noBlog);
        Button setUpBlog = (Button) view1.findViewById(R.id.setUpBlog);
        noBlog.setTypeface(domineBold);
        setUpBlog.setTypeface(domineBold);
        return view1;
    }

}
