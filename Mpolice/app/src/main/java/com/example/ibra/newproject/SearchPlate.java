package com.example.ibra.newproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SearchPlate extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private Uri fileUri =null;
    ImageView imageV = null;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageV = (ImageView) findViewById(R.id.imageView2);
        imageV.setImageDrawable(null);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchPlate.this, Report.class);
                startActivity(i);
            }
        });

    }




    public void takePic(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getOutputPhotoFile();
        fileUri = Uri.fromFile(getOutputPhotoFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    private File getOutputPhotoFile(){
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),getPackageName());
        if (!directory.exists()){
            if (!directory.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
        return new File(directory.getPath() + File.separator + "IMG_"+timeStamp + ".jpg");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //InputStream stream= null;
        Uri photoUri = null;
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            Log.d("Camera", "Success");
                if (data == null){
                    Toast.makeText(this, "Image saved successfully",
                            Toast.LENGTH_LONG).show();
                    photoUri = fileUri;
                    showPhoto(photoUri);
                }else {
                    photoUri = data.getData();
                    Toast.makeText(this,"Image saved in: "+data.getData(),Toast.LENGTH_LONG).show();
                    showPhoto(photoUri);
                }
            }else if (resultCode == RESULT_CANCELED){
                //User cancelled image capture
                Toast.makeText(this,"Image capture cancelled",Toast.LENGTH_LONG).show();
            }else {
                //Image capture failed
                Toast.makeText(this,"Image captured failed, please try again",Toast.LENGTH_LONG).show();
            }
        }

    private void showPhoto(Uri photoUri){
        File imgFile = new File(photoUri.getPath());
        if (imgFile.exists()){
            Drawable old = imageV.getDrawable();
            if (old !=null){
                ((BitmapDrawable)old).getBitmap().recycle();
                Log.d("camera", "replace");
            }
            Log.d("camera", "new");
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            BitmapDrawable drawable = new BitmapDrawable(this.getResources(),bitmap);
            imageV.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageV.setImageDrawable(drawable);
        }

    }
    }
