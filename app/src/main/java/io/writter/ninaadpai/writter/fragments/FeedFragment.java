package io.writter.ninaadpai.writter.fragments;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.writter.ninaadpai.writter.DashboardActivity;
import io.writter.ninaadpai.writter.adapters.FeedListAdapter;
import io.writter.ninaadpai.writter.R;
import io.writter.ninaadpai.writter.classes.Answer;
import io.writter.ninaadpai.writter.classes.Question;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class FeedFragment extends Fragment{
    static FirebaseAuth firebaseAuth;
    static DatabaseReference databaseReference;
    List<Question> posts = new ArrayList<>();
    List<Answer> answers;
    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Demo","FeedFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        final FragmentActivity f = getActivity();
        final TextView feedTitle = (TextView)view.findViewById(R.id.feedTitle);

        final RecyclerView feedRecycler = (RecyclerView)view.findViewById(R.id.feedRecycler);
        LinearLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 1);
        feedRecycler.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference questionsRef = FirebaseDatabase.getInstance().getReference().child("questions_pool");
        questionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot questionSnapShot : dataSnapshot.getChildren()) {
                    final String questionId = questionSnapShot.getKey().toString();
                    final String userId = questionSnapShot.child("userId").getValue().toString();
                    final String topic = questionSnapShot.child("category").getValue().toString();
                    final String questionText = questionSnapShot.child("questionText").getValue().toString();
                    final String  uploadTime = questionSnapShot.child("uploadTime").getValue().toString();
                    final boolean  anonymous = (boolean) questionSnapShot.child("anonymous").getValue();
                    DataSnapshot answerSnapShot = dataSnapshot.child(questionId).child("answers");

                    answers = new ArrayList<>();
                    Iterable<DataSnapshot> answerIterable = answerSnapShot.getChildren();
                    for(DataSnapshot answer  : answerIterable) {
                        Answer a = new Answer(answer.child("providerId").getValue().toString(), answer.child("providerName").getValue().toString(), answer.child("questionId").getValue().toString(), answer.child("answerText").getValue().toString(), answer.child("timeStamp").getValue(), (boolean) answer.child("anonymous").getValue(), null);
                        answers.add(a);
                    }
                    posts.add(new Question(questionId, userId, questionText, topic, anonymous, uploadTime, null, null,answers));

                }

                Collections.sort(posts, new Comparator<Question>() {
                    @Override
                    public int compare(Question o1, Question o2) {
                        long time1 = Long.parseLong(String.valueOf(o1.getUploadTime()));
                        long time2 = Long.parseLong(String.valueOf(o2.getUploadTime()));
                        if(time1 < time2)
                            return 1;
                        else
                        return -1;
                    }
                });
                if(posts.size() == 0) {
                    feedTitle.setText("Currently there are no posts in your feed.");
                }
                final FeedListAdapter feedListAdapter = new FeedListAdapter(f, posts);
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

    public static void postAnswer(final String questionId, final String answer, final boolean anonymous) {
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final DatabaseReference currentUserNameRef = databaseReference.child(currentUser.getUid()).child("userName");
        currentUserNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentUserName = dataSnapshot.getValue().toString();

                final Object answerTimeStamp= ServerValue.TIMESTAMP;
                databaseReference.child("questions_pool").child(questionId).child("answers").push().setValue(new Answer(currentUser.getUid(),currentUserName,questionId, answer, answerTimeStamp, anonymous, null));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}