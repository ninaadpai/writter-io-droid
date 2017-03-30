package io.writter.ninaadpai.writter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView appTitle, subTitle, alternateOr, useGoogle, useFacebook, whatsThis, termsOfService;
    Typeface novaOval;
    Typeface domineBold;
    EditText emailEdit, passwordEdit;
    Button signupbtn, loginbtn;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appTitle = (TextView)findViewById(R.id.appTitle);
        subTitle = (TextView)findViewById(R.id.subTitle);
        alternateOr = (TextView)findViewById(R.id.alternateOr);
        useGoogle = (TextView)findViewById(R.id.google);
        useFacebook = (TextView)findViewById(R.id.facebook);
        whatsThis = (TextView)findViewById(R.id.whatsThis);
        termsOfService = (TextView)findViewById(R.id.termsOfService);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        emailEdit = (EditText)findViewById(R.id.emailEdit);
        signupbtn = (Button) findViewById(R.id.signupbtn);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        novaOval = Typeface.createFromAsset(getAssets(),"fonts/NovaOval.ttf");
        domineBold = Typeface.createFromAsset(getAssets(),"fonts/FiraSansCondensed-Regular.ttf");
        appTitle.setTypeface(novaOval);
        subTitle.setTypeface(domineBold);
        emailEdit.setTypeface(domineBold);
        passwordEdit.setTypeface(domineBold);
        signupbtn.setTypeface(domineBold);
        loginbtn.setTypeface(domineBold);
        alternateOr.setTypeface(domineBold);
        useGoogle.setTypeface(domineBold);
        useFacebook.setTypeface(domineBold);
        whatsThis.setTypeface(domineBold);
        termsOfService.setTypeface(domineBold);
        termsOfService.setText(Html.fromHtml("By clicking Sign Up you automatically agree to the <u>Terms of Use</u>"));
        termsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TermsOfUseActivity.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseUser = null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(MainActivity.this, DashboardActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d("User State", "onAuthStateChanged:signed_out");
        }
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {
        String email = emailEdit.getText().toString().trim();
        String pass = passwordEdit.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Log In").setMessage("Please enter email and password correctly.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(false);
            AlertDialog alert = builder.create();
            alert.show();
        }

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            finish();
                            Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(i);
                        }
                        else {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void RegisterUser() {
        String email = emailEdit.getText().toString().trim();
        String pass = passwordEdit.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Sign Up").setMessage("Please enter email and password correctly.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(false);
            AlertDialog alert = builder.create();
            alert.show();
        }

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            finish();
                            Intent i = new Intent(MainActivity.this, SetUpNameActivity.class);
                            startActivity(i);
                        }
                        else{
                            progressDialog.dismiss();
                            View layoutValue = LayoutInflater.from(MainActivity.this).inflate(R.layout.signup_custom_toast, null);
                            TextView custom_toast_message = (TextView)layoutValue.findViewById(R.id.custom_toast_message);
                            custom_toast_message.setTypeface(domineBold);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 600);
                            toast.setView(layoutValue);//setting the view of custom toast layout
                            toast.show();
                        }
                    }
                });
    }
}
