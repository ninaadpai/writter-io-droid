package io.writter.ninaadpai.writter.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dant.centersnapreyclerview.SnappingRecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.writter.ninaadpai.writter.adapters.ExampleDateAdapter;
import io.writter.ninaadpai.writter.adapters.ExampleDateEndPaddingItemDecoration;
import io.writter.ninaadpai.writter.MainActivity;
import io.writter.ninaadpai.writter.R;

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
    TextView tagLine, birthday, memberSince, institute, work, placesLived, nothingtoshow;
    InputMethodManager inputManager;
    SnappingRecyclerView profileExplorer;
    private FragmentManager fragmentManager;
    HashMap<String, String> institutesMap = new HashMap<>();
    HashMap<String, String> workMap = new HashMap<>();
    List<String> placesLivedList = new ArrayList<>();
    String name, tagline;
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
        tagLine = (TextView) view.findViewById(R.id.tagLine);
        birthday = (TextView) view.findViewById(R.id.birthday);
        memberSince = (TextView) view.findViewById(R.id.memberSince);
        institute = (TextView) view.findViewById(R.id.institute);
        work = (TextView) view.findViewById(R.id.work);
        placesLived = (TextView) view.findViewById(R.id.placesLived);
        nothingtoshow = (TextView) view.findViewById(R.id.nothingtoshow);
        birthday.setVisibility(View.GONE);
        institute.setVisibility(View.GONE);
        work.setVisibility(View.GONE);
        placesLived.setVisibility(View.GONE);
        nothingtoshow.setVisibility(View.GONE);
        profileImage = (ImageView)view.findViewById(R.id.profileImage);
        firebaseAuth = firebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();
        fragmentManager = getActivity().getSupportFragmentManager();
        profileExplorer = (SnappingRecyclerView) view.findViewById(R.id.profileExplorer);
        profileExplorer.addItemDecoration(new ExampleDateEndPaddingItemDecoration(profileExplorer.getOrientation()));
        profileExplorer.setAdapter(new ExampleDateAdapter());
        inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                     name = String.valueOf(dataSnapshot.child("userName").getValue());
                    userName.setText(name);
                    tagline = String.valueOf(dataSnapshot.child("tagLine").getValue());
                    if(tagline.isEmpty())
                        tagLine.setText("Set a tag line");
                    else
                        tagLine.setText(tagline.toString());
                String photoLink = String.valueOf(dataSnapshot.child("profile_photo").child("encodedSchemeSpecificPart").getValue());
                if(photoLink.isEmpty()) {
                    profileImage.setImageResource(R.drawable.default_profile);
                    }
                else {
                    Picasso.with(getContext())
                            .load("https:"+photoLink)
                            .rotate(0)
                            .transform(new CircleTransform())
                            .into(profileImage);
                }
                String member = String.valueOf(dataSnapshot.child("memberSince").getValue());
                long millisecond = Long.parseLong(member);
                String memberSinceString = DateFormat.format("MMM dd, yyyy", new Date(millisecond)).toString();
                String birthdayString = String.valueOf(dataSnapshot.child("birthday").getValue());

                memberSince.setText("Member since: "+memberSinceString);
                DataSnapshot instituteRef = dataSnapshot.child("institutions");
                for(DataSnapshot insti : instituteRef.getChildren()) {
                    institutesMap.put(insti.getKey(), String.valueOf(insti.getValue()));
                }
                DataSnapshot workRef = dataSnapshot.child("work");
                for(DataSnapshot worked : workRef.getChildren()) {
                    workMap.put(worked.getKey(), String.valueOf(worked.getValue()));
                }
                DataSnapshot placesLivedData = dataSnapshot.child("placesLived");
                for(DataSnapshot places : placesLivedData.getChildren()) {
                    placesLivedList.add(String.valueOf(places.getValue()));
                }
                Log.i("Bday, places lived",birthdayString+", "+placesLivedList);
                if(birthdayString.isEmpty()|| placesLivedList.isEmpty()) {
                    nothingtoshow.setVisibility(View.VISIBLE);
                }
                else {
                    birthday.setVisibility(View.VISIBLE);
                    institute.setVisibility(View.VISIBLE);
                    work.setVisibility(View.VISIBLE);
                    placesLived.setVisibility(View.VISIBLE);
                }
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

        view.findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateEditProfileWindow(getActivity(), name, tagline);
            }
        });

        tagLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateTagLine(getActivity());
            }
        });
        return view;
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

    private void inflateTagLine(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        final View dialogLayout = inflater.inflate(R.layout.set_tagline_window, null);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setView(dialogLayout, 0, 0, 0, 0);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wlmp = dialog.getWindow()
                .getAttributes();
        wlmp.gravity = Gravity.CENTER;
        builder.setView(dialogLayout);
        dialog.show();
        final EditText tagLineEdit =(EditText) dialog.findViewById(R.id.tagLineEdit);
        dialog.findViewById(R.id.exitTag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                tagLineEdit.setText("");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.updateTag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTag = tagLineEdit.getText().toString().trim();
                if(updatedTag.length() <= 100) {
                    databaseReference.child(user.getUid()).child("tagLine").setValue(updatedTag.toString().trim());
                    Log.i("Updated tag",updatedTag);
                    tagLineEdit.setText("");
                    dialog.dismiss();
                }
            }
        });
    }

    private void inflateEditProfileWindow(Context context, String name, String tagline) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        final View dialogLayout = inflater.inflate(R.layout.profile_edit_window,
                null);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setView(dialogLayout, 0, 0, 0, 0);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wlmp = dialog.getWindow()
                .getAttributes();
        wlmp.gravity = Gravity.CENTER;

        builder.setView(dialogLayout);
        dialog.show();

        dialog.findViewById(R.id.exitEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView nameText = (TextView) dialog.findViewById(R.id.nameEdit);
        TextView tagText = (TextView) dialog.findViewById(R.id.tagEdit);
        nameText.setText(name);
        if(!(tagline.isEmpty()))
        tagText.setText(tagline);
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
        wlmp.gravity = Gravity.CENTER;
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


    public interface imageUpload {
        public void startImageUpload();
        public void stopImageUpload();
    }

}
