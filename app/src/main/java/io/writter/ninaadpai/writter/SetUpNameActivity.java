package io.writter.ninaadpai.writter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SetUpNameActivity extends AppCompatActivity {
    TextView appTitle;
    TextView setupHint;
    TextView chooseTopicsHint;
    static TextView recyclerTitle;
    EditText firstLastName;
    static Typeface domineBold;
    ListView topicList;
    TopicAdapter adapter;
    FavoriteAdapter fadapter;
    RecyclerView frecyclerView;
    public static Button goBtn;
    public static TextView topicEditText;
    AlertDialog.Builder builder;
    List<String> topics;
    public static List<String> favorites;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseAuth mAuthListener;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_name);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        appTitle = (TextView)findViewById(R.id.appTitle);
        firstLastName = (EditText) findViewById(R.id.firstLastName);
        topicEditText = (TextView) findViewById(R.id.topicEditText);
        setupHint = (TextView) findViewById(R.id.setupHint);
        chooseTopicsHint = (TextView) findViewById(R.id.chooseTopicsHint);
        recyclerTitle = (TextView) findViewById(R.id.chosenFavorites);
        frecyclerView = (RecyclerView) findViewById(R.id.favoriteRecycler);
        goBtn = (Button) findViewById(R.id.gobtn1);
        domineBold = Typeface.createFromAsset(getAssets(),"fonts/NotoSans-Bold.ttf");
        goBtn.setTypeface(domineBold);
        goBtn.setText("SKIP FOR NOW");
        favorites = new ArrayList<>();
        firstLastName.setTypeface(domineBold);
        topicEditText.setTypeface(domineBold);
        setupHint.setTypeface(domineBold);
        topicEditText.setTypeface(domineBold);
        recyclerTitle.setTypeface(domineBold);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        topics = new ArrayList<>();
        topics.add("Technology");
        topics.add("Fashion");
        topics.add("Entertainment");
        topics.add("Health");
        topics.add("Literature");
        topics.add("Cooking");
        topics.add("Education");
        topics.add("Music");
        topics.add("History");
        topics.add("Business");
        topics.add("Science");
        topics.add("Politics");
        topics.add("Mathematics");
        topics.add("Architecture");
        topics.add("Automobile");
        topics.add("Energy");
        topics.add("Geography");
        topics.add("Travel");
        topics.add("Writing");
        topicList = (ListView) findViewById(R.id.topicList);
        populateList(topics);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = firstLastName.getText().toString().trim();
                if(!(name.matches("(.*) (.*)")) || TextUtils.isEmpty(name)) {
                    builder = new AlertDialog.Builder(SetUpNameActivity.this);
                    builder.setTitle("Your Name").setMessage("Please enter a proper first and last name.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setCancelable(false);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else {

                    WritterUser user =  new WritterUser(name, favorites);
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    databaseReference.child(firebaseUser.getUid()).setValue(user);
                    if(firebaseUser!=null){
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name).build();
                                firebaseUser.updateProfile(profileUpdates);
                                Intent intent = new Intent(SetUpNameActivity.this, DashboardActivity.class);
                                startActivity(intent);

                        }

                }
            }
        });
        topicEditText.setText("Add at least "+(5-favorites.size())+" topics to personalize your home.");
        topicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!(favorites.contains(topics.get(position)))){
                    favorites.add(0,topics.get(position));
                    populateFavorites(favorites);
                    if(favorites.size() == 0) {
                        goBtn.setText("SKIP FOR NOW");
                        recyclerTitle.setText("Nothing Selected");
                    }
                    else if(favorites.size() > 0) {
                        goBtn.setText("PROCEED");
                        recyclerTitle.setText("My favorites");
                    }

                    if(favorites.size() >= 5){
                        topicEditText.setText("You can choose more topics or proceed ahead.");
                    }
                    else if(favorites.size() <5){
                        topicEditText.setText("Add at least "+(5-favorites.size())+" topics to personalize your home.");
                    }
                }
           }
        });
    }

    private void populateFavorites(List<String> favorites) {
        LinearLayoutManager mLinearLayoutManager = new GridLayoutManager(this, 1);
        frecyclerView.setLayoutManager(mLinearLayoutManager);
        fadapter = new FavoriteAdapter(SetUpNameActivity.this,favorites, domineBold);
        frecyclerView.setItemAnimator(new DefaultItemAnimator());
        frecyclerView.setAdapter(fadapter);
    }

    private void populateList(List<String> topics) {
        adapter = new TopicAdapter(this, R.layout.setup_listview_row, topics, domineBold);
        topicList.setAdapter(adapter);
    }
}
