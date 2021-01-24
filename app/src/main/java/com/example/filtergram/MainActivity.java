package com.example.filtergram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button choosePhotoButton, saveImageButton;
    private Bitmap image;
    private Spinner selectFilterSpinner;
    private String[] filterArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view);
        choosePhotoButton = findViewById(R.id.ChoosePhotoButton);
        selectFilterSpinner = findViewById(R.id.filterSpinner);
        saveImageButton = findViewById(R.id.SaveImage);
        filterArray = new String[]{"Select Filter","Toon", "Sepia", "Contrast", "Invert","Pixelation","Sketch","Swirl","Vignette"};
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,filterArray);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectFilterSpinner.setAdapter(aa);
        selectFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1 :
                        Glide
                                .with(MainActivity.this)
                                .load(image)
                                .apply(RequestOptions.bitmapTransform(new ToonFilterTransformation()))
                                .into(imageView);
                        break;
                    case 2 :
                        Glide
                                .with(MainActivity.this)
                                .load(image)
                                .apply(RequestOptions.bitmapTransform(new SepiaFilterTransformation()))
                                .into(imageView);
                        break;
                    case 3 :
                        Glide
                                .with(MainActivity.this)
                                .load(image)
                                .apply(RequestOptions.bitmapTransform(new ContrastFilterTransformation()))
                                .into(imageView);
                        break;
                    case 4 :
                        Glide
                                .with(MainActivity.this)
                                .load(image)
                                .apply(RequestOptions.bitmapTransform(new InvertFilterTransformation()))
                                .into(imageView);
                        break;
                    case 5 :
                        Glide
                                .with(MainActivity.this)
                                .load(image)
                                .apply(RequestOptions.bitmapTransform(new PixelationFilterTransformation()))
                                .into(imageView);
                        break;
                    case 6 :
                        Glide
                                .with(MainActivity.this)
                                .load(image)
                                .apply(RequestOptions.bitmapTransform(new SketchFilterTransformation()))
                                .into(imageView);
                        break;
                    case 7 :
                        Glide
                                .with(MainActivity.this)
                                .load(image)
                                .apply(RequestOptions.bitmapTransform(new SwirlFilterTransformation()))
                                .into(imageView);
                        break;
                    case 8 :
                        Glide
                                .with(MainActivity.this)
                                .load(image)
                                .apply(RequestOptions.bitmapTransform(new VignetteFilterTransformation()))
                                .into(imageView);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choosePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent,123);
            }
        });
        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.buildDrawingCache();
                Bitmap bm=imageView.getDrawingCache();
                MediaStore.Images.Media.insertImage(getContentResolver(), bm, "first Image" , "");
                Toast.makeText(getApplicationContext(),"Photo has been saved.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && data != null){
            Log.d("abhishek", "onActivityResult: "+requestCode);
            Uri uri  = data.getData();
            try {
                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri,"r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                imageView.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}