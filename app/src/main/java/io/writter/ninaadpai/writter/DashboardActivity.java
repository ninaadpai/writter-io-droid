package io.writter.ninaadpai.writter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    Class fragmentClass;
    Fragment fragment = FeedFragment.class.newInstance();

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
        Fragment fragment = null;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu,menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Demo","Dashboard: onCreate before");
        setContentView(R.layout.activity_dashboard);
        Log.i("Demo","Dashboard: onCreate after");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, new FeedFragment()).commit();
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
