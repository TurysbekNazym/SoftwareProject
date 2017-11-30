package com.example.admin.menagmenttool;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class LoadImage extends AppCompatActivity {


    public ImageView image;
    public EditText text;
    public Button btn_add;
    public Button btn_load;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private Uri imgUri;
    ProgressDialog progressDialog ;

    public static final String STORAGE_PATH = "image/";
    public static final String DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        image = (ImageView) findViewById(R.id.image);
        text = (EditText) findViewById(R.id.description);
        btn_add = (Button) findViewById(R.id.add);
        btn_load = (Button) findViewById(R.id.upload);
        progressDialog = new ProgressDialog(LoadImage.this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"),REQUEST_CODE);
            }
        });

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings("VisibleForTests")
            public void onClick(View view) {
                if (imgUri != null) {
                    // Setting progressDialog Title.
                    progressDialog.setTitle("Uploading...");
                    // Showing progressDialog.
                    progressDialog.show();
                    StorageReference ref = mStorageRef.child(STORAGE_PATH + System.currentTimeMillis() + "." + GetImageExtension(imgUri));

                    ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            Image img = new Image(text.getText().toString(),taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(img);

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();
                                    // Showing toast message after done uploading.
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                    double progress = (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"");
                                }
                            });
                }
                else {

                    Toast.makeText(LoadImage.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imgUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);

                // Setting up bitmap selected image into ImageView.
                image.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                //ChooseButton.setText("Image Selected");

            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String GetImageExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


}
