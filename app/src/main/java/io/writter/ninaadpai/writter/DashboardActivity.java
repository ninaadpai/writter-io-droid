package io.writter.ninaadpai.writter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    Class fragmentClass;
    EditText searchFeed;
    Typeface domineBold;
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
            case R.id.navigation_trending:
                fragmentClass = TrendingFragment.class;
                break;
            case R.id.navigation_notifications:
                fragmentClass = NotificationsFragment.class;
                break;
            case R.id.navigation_profile:
                fragmentClass = ProfileFragment.class;
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
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, new FeedFragment()).commit();
        final InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        searchFeed = (EditText)findViewById(R.id.searchFeed);
        final ImageView clearSearch = (ImageView) findViewById(R.id.clearSearch);
        searchFeed.setFocusableInTouchMode(false);
        searchFeed.setFocusable(false);
        searchFeed.setFocusableInTouchMode(true);
        searchFeed.setFocusable(true);
        domineBold = Typeface.createFromAsset(getAssets(),"fonts/RobotoSlab-Regular.ttf");
        clearSearch.setVisibility(View.INVISIBLE);
        inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        searchFeed.setTypeface(domineBold);
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
                //Log.i("Typing:", s.toString());
                List<String> best = null;
                best = new GetSearchMatches(DashboardActivity.this).execute(s.toString(), list);
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

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        clearSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchFeed.setText("");
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
}
