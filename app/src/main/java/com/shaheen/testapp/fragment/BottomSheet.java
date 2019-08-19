package com.shaheen.testapp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shaheen.testapp.R;
import com.shaheen.testapp.progressdialog.WorkingProgressDialog;
import com.shaheen.testapp.databaseRef.FeaturedRef;
import com.shaheen.testapp.model.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.hdodenhof.circleimageview.CircleImageView;

public class BottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    Button BTNsubmit;
    CircleImageView profile_image;
    ArrayList<String> returnValue = new ArrayList<>();
    EditText ET_tiktokID;
    EditText ET_tiktokURL;
    View view;
    Bitmap myBitmap = null;
    FirebaseStorage storage;
    StorageReference storageReference;
    private WorkingProgressDialog dialog = null;
    UploadTask uploadTask;
    private ProgressDialog progressDialog;

    public static BottomSheet newInstance() {
        return new BottomSheet();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottomsheet, container,
                false);
        profile_image = view.findViewById(R.id.profile_image);
        BTNsubmit = view.findViewById(R.id.submit);
        ET_tiktokID = view.findViewById(R.id.tiktok_id);
        ET_tiktokURL = view.findViewById(R.id.profile);

        profile_image.setOnClickListener(this);
        BTNsubmit.setOnClickListener(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        dialog = new WorkingProgressDialog(Objects.requireNonNull(getActivity()));

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

                    mShowImage(returnValue.get(0));

                }
            }
            break;
        }
    }

    private void mShowImage(String path) {

        File imgFile = new File(path);

        if (imgFile.exists()) {

            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            profile_image.setImageBitmap(myBitmap);

        }

    }

    @Override
    public void onClick(View v) {
        if (v == profile_image) {

            Options options = Options.init()
                    .setRequestCode(100)
                    .setCount(1);
            Pix.start(this, options);
        }
        if (v == BTNsubmit) {

            if (valid()) {

                showProgressDialog();
                mAddProfileToFirebase();

            }
        }
    }

    private void mAddProfileToFirebase() {


        mUploadProfilePic();


    }

    private void mUploadProfilePic() {

        final StorageReference ref = storageReference.child("images").child("featured").child(UUID.randomUUID().toString());



        uploadTask = ref.putFile(Uri.fromFile(mReduceImageSize(new File(returnValue.get(0)))));

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String downloadURL = downloadUri.toString();

                    mAddProfileToFirebaseDB(downloadURL);


                } else {
                    Toast.makeText(getActivity(), "Operation failed. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void mAddProfileToFirebaseDB(String downloadURL) {

        Profile profile = new Profile();
        profile.setImg_url(downloadURL);
        profile.setTiktok_id(ET_tiktokID.getText().toString());
        profile.setProfileURL(ET_tiktokURL.getText().toString());

        FeaturedRef.getInstance(getActivity()).getRef().push().setValue(profile);

        Toast.makeText(getActivity(), "Profile added successfully", Toast.LENGTH_SHORT).show();

        ET_tiktokURL.setText("");
        ET_tiktokID.setText("");
        myBitmap=null;

        hideProgressDialog();
        dismiss();
    }

    private boolean valid() {

        if (returnValue.size() == 0) {

            Toast.makeText(getActivity(), "Please add a profile pic to continue", Toast.LENGTH_SHORT).show();

            return false;

        } else if (TextUtils.isEmpty(ET_tiktokID.getText().toString())) {

            Toast.makeText(getActivity(), "Please enter a valid TikTok Id to continue", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!ET_tiktokURL.getText().toString().toLowerCase().contains("tiktok")) {

            Toast.makeText(getActivity(), "Please enter a valid profile URL to continue", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }







    public File mReduceImageSize(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }


    void showProgressDialog() {
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    void hideProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }



}