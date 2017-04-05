package io.writter.ninaadpai.writter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import io.writter.ninaadpai.writter.classes.Answer;
import io.writter.ninaadpai.writter.classes.Question;
import io.writter.ninaadpai.writter.classes.QuestionPool;
import io.writter.ninaadpai.writter.fragments.BlogFragment;
import io.writter.ninaadpai.writter.fragments.FeedFragment;
import io.writter.ninaadpai.writter.fragments.ProfileFragment;
import io.writter.ninaadpai.writter.fragments.SearchFragment;

public class DashboardActivity extends AppCompatActivity implements SearchFragment.IQuestion, ProfileFragment.imageUpload, FeedFragment.feedInterface{

    Class fragmentClass;
    EditText searchFeed;
    Typeface domineBold;
    ProgressDialog progressDialog;
    ImageView clearSearch;
    InputMethodManager inputManager;
    AlertDialog.Builder questionPostedDialog;
    String imgUrl;
    Fragment fragment = FeedFragment.class.newInstance();
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
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private FragmentManager fragmentManager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectedFragment(item);
            return true;
        }
    };

    public DashboardActivity() throws IllegalAccessException, InstantiationException {
    }

    private void selectedFragment(MenuItem item) {
        fragmentClass = null;
        switch(item.getItemId()) {
            case R.id.navigation_home:
                fragmentClass = FeedFragment.class;
                break;
            case R.id.navigation_blog:
                fragmentClass = BlogFragment.class;
                break;
            case R.id.navigation_notifications:
                fragmentClass = NotificationsFragment.class;
                break;
            case R.id.navigation_profile:
                fragmentClass = ProfileFragment.class;
//                firebaseAuth.getInstance().signOut();
//                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
//                finish();
                break;
            default:
                fragmentClass = FeedFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        }catch(Exception e) {
            e.printStackTrace();
        }
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Demo","Dashboard: onCreate before");
        setContentView(R.layout.activity_dashboard);
        Log.i("Demo","Dashboard: onCreate after");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        FrameLayout touchInterceptor = (FrameLayout)findViewById(R.id.flContent);
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, new FeedFragment()).commit();
        inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        searchFeed = (EditText)findViewById(R.id.searchFeed);
        clearSearch = (ImageView) findViewById(R.id.clearSearch);
        searchFeed.setFocusableInTouchMode(false);
        searchFeed.setFocusable(false);
        searchFeed.setFocusableInTouchMode(true);
        searchFeed.setFocusable(true);
        domineBold = Typeface.createFromAsset(getAssets(),"fonts/FiraSansCondensed-Regular.ttf");
        clearSearch.setVisibility(View.INVISIBLE);
        inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        searchFeed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @TargetApi(Build.VERSION_CODES.CUPCAKE)
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    clearSearch.setVisibility(View.VISIBLE);
                }
                else if(!hasFocus) {
                    clearSearch.setVisibility(View.INVISIBLE);
                    inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                    tx.replace(R.id.flContent, new FeedFragment()).commit();
                }
            }
        });
        searchFeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> best = null;
                best = new GetSearchMatches(DashboardActivity.this).execute(s.toString(), list);
                if(best != null) {
                    if(best.size() > 0) {
                        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("message", (ArrayList<String>) best);
                        SearchFragment fragInfo = new SearchFragment();
                        fragInfo.setArguments(bundle);
                        tx.replace(R.id.flContent, fragInfo);
                        tx.commit();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        clearSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(searchFeed.getText().toString().isEmpty()){
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                v = getCurrentFocus();
                if ( v instanceof EditText) {
                    Rect outRect = new Rect();
                    v.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                        v.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                else {
                    searchFeed.setText("");
                }
                return false;
            }
        });

        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v = getCurrentFocus();
                if ( v instanceof EditText) {
                    Rect outRect = new Rect();
                    v.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                        v.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Demo","Dashboard: onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Demo","Dashboard: onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Demo","Dashboard: onResume");
    }

    @Override
    public void postQuestion(final boolean anonymously) {
        String text = searchFeed.getText().toString().trim();
        final StringBuilder questionText = new StringBuilder();
        questionText.append(text.substring(0,1).toUpperCase() + text.substring(1));
        final Object questionTimeStamp= ServerValue.TIMESTAMP;
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String currentUid = currentUser.getUid();
        databaseReference.child("questions_pool").push().setValue(new Question("",currentUid, questionText.toString(),"General", anonymously ,questionTimeStamp, null, null, null));
    }

    @Override
    public void destroySearchFragment() throws IllegalAccessException, InstantiationException {
        clearSearch.setVisibility(View.INVISIBLE);
        searchFeed.setText("");
        inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, FeedFragment.class.newInstance()).commit();
        recreate();
        View layoutValue = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.question_posted_successfully, null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setView(layoutValue);//setting the view of custom toast layout
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 5000);
    }

    @Override
    public void sanitizeQuestionText() {
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\/<>,.";
        String questionStr;
        questionStr = searchFeed.getText().toString().trim();
        if(!(questionStr.substring(questionStr.length() - 1).equals("?"))){
            questionStr = questionStr + "?";
            searchFeed.setText(questionStr.toString());
        }
    }

    @Override
    public void startUpload() {
        progressDialog = new ProgressDialog(DashboardActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Posting Your Question...");
        progressDialog.show();
    }

    @Override
    public void doneUpload() {
        progressDialog.dismiss();
    }

    @Override
    public void startImageUpload() {
        progressDialog = new ProgressDialog(DashboardActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading Your Profile Photo...");
        progressDialog.show();
    }

    @Override
    public void stopImageUpload() {
        progressDialog.dismiss();
    }

    @Override
    public void doRecreate() {
        recreate();
    }
}
