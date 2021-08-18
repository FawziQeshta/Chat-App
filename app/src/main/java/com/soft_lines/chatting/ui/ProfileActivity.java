package com.soft_lines.chatting.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.soft_lines.chatting.R;
import com.soft_lines.chatting.databinding.ActivityProfileBinding;
import com.soft_lines.chatting.utils.Constants;
import com.soft_lines.chatting.utils.PreferencesManager;

import java.util.HashMap;

public class ProfileActivity extends BaseActivity {

    private ActivityProfileBinding binding;
    private PreferencesManager preferencesManager;
    private String userId;
    private boolean isOtherUser = false;
    private final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadIntentData();

        loadProfileData();
        setListeners();

    }

    private void loadIntentData() {
        preferencesManager = new PreferencesManager(getApplicationContext());

        Intent intent = getIntent();
        userId = intent.getStringExtra(Constants.KEY_USER_ID);
        isOtherUser = intent.getBooleanExtra(Constants.KEY_OTHER_USER, false);

        if (isOtherUser) {
            binding.textTitle.setText(R.string.user_profile);
            binding.imageSignOut.setVisibility(View.INVISIBLE);
        }
    }

    private void setListeners() {
        binding.imageSignOut.setOnClickListener(v -> {
            signOut();
        });

        binding.imageBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.viewPhone.setOnClickListener(v -> {
            if (isOtherUser) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + binding.inputPhone.getText().toString()));
                startActivity(intent);
            }
        });
    }

    private void loadProfileData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection(Constants.KEY_COLLECTION_USERS).document(userId);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null) {
                    DocumentSnapshot document = task.getResult();

                    String image = document.getString(Constants.KEY_IMAGE);
                    String name = document.getString(Constants.KEY_NAME);
                    String email = document.getString(Constants.KEY_EMAIL);
                    String phone = document.getString(Constants.KEY_PHONE);

                    binding.imageProfile.setImageBitmap(loadUserDetails(image));
                    binding.inputName.setText(name);
                    binding.inputEmail.setText(email);
                    binding.inputPhone.setText(phone);

                    binding.containerProgressbar.setVisibility(View.GONE);
                }
            } else {
                Log.e(TAG, task.getException() + "");
            }
        }).addOnFailureListener(e -> showToast(e.getMessage()));

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Bitmap loadUserDetails(String userImage) {
        byte[] bytes = Base64.decode(userImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void signOut() {
        showToast(getString(R.string.signing_out));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(Constants.KEY_COLLECTION_USERS)
                .document(preferencesManager.getString(Constants.KEY_USER_ID));
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferencesManager.clear();
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(e -> showToast(getString(R.string.unable_to_sign_out)));
    }

}