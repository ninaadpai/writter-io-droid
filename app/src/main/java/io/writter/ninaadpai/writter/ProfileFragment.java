package io.writter.ninaadpai.writter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import org.w3c.dom.Text;

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
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Demo","ProfileFragment onCreateView");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        TextView userName = (TextView) view.findViewById(R.id.userName);
        TextView tagLine = (TextView) view.findViewById(R.id.tagLine);
        TextView location = (TextView) view.findViewById(R.id.location);
        firebaseAuth = firebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        profileImage = (ImageView)view.findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
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
                   // Bitmap resized = resizeBitmap(String.valueOf(taskSnapshot.getDownloadUrl()), 120, 100);
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

    public interface onFragmentInteractionListener {
        public void moveToNextFragment();
    }

    public interface imageUpload {
        public void startImageUpload();
        public void stopImageUpload();
    }

}
