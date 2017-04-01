package io.writter.ninaadpai.writter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.io.File;
import java.net.URL;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private static final int GALLERY_INTENT = 2;
    FirebaseUser user;
    ImageView profileImage;
    private PopupWindow pw;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Demo","ProfileFragment onCreateView");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView userName = (TextView) view.findViewById(R.id.userName);
        final TextView tagLine = (TextView) view.findViewById(R.id.tagLine);
        final TextView location = (TextView) view.findViewById(R.id.location);
        profileImage = (ImageView)view.findViewById(R.id.profileImage);
        firebaseAuth = firebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = String.valueOf(dataSnapshot.child("userName").getValue());
                    userName.setText(name);
                    String tagline = String.valueOf(dataSnapshot.child("tagLine").getValue());
                    if(tagline == null)
                        tagLine.setText("Set a tag line");
                    else
                        tagLine.setText(tagline.toString());
                     String Location = String.valueOf(dataSnapshot.child("location").getValue());
                if(Location == null)
                    location.setText("Set a home location");
                else
                    location.setText(Location.toString());
                    Picasso.with(getContext())
                        .load("https:"+String.valueOf(dataSnapshot.child("profile_photo").child("encodedSchemeSpecificPart").getValue()))
                        .rotate(0)
                        .transform(new CircleTransform())
                        .into(profileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        view.findViewById(R.id.optButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateOptionsWindow(getActivity());
            }
        });
        return view;
    }

    private void inflateOptionsWindow(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        final View dialogLayout = inflater.inflate(R.layout.profile_options_window,
                null);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setView(dialogLayout, 0, 0, 0, 0);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wlmp = dialog.getWindow()
                .getAttributes();
        wlmp.gravity = Gravity.BOTTOM;
        dialogLayout.findViewById(R.id.logOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        builder.setView(dialogLayout);
        dialog.show();

        dialog.findViewById(R.id.exitOptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK) {
            mListener.startImageUpload();
            final Uri uri = data.getData();
            final StorageReference filepath = storageReference.child("profile_photos").child(user.getUid()).child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    databaseReference.child(user.getUid()).child("profile_photo").setValue(taskSnapshot.getDownloadUrl());
                    Picasso.with(getContext())
                            .load(String.valueOf(taskSnapshot.getDownloadUrl()))
                            .rotate(90)
                            .transform(new CircleTransform())
                            .into(profileImage);
                        mListener.stopImageUpload();
                }
            });
        }
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
    imageUpload mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Demo","ProfileFragment onAttach");
        try{
            mListener = (ProfileFragment.imageUpload) context;
        }catch(ClassCastException e) {
            throw new ClassCastException(context.toString()+" should implement imageUpload.");
        }

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
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    public interface imageUpload {
        public void startImageUpload();
        public void stopImageUpload();
    }

}
