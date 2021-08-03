package com.example.practice_retrofit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

public class MainActivity extends AppCompatActivity {
    Button b_choose, b_upload;  Bitmap bitmap_chosen;
    String imagePath;
    List<String> list = new ArrayList<>();
    public static int GET_FROM_GALLERY=3;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.thedogapi.com/v1/images/").addConverterFactory(GsonConverterFactory.create()).build();
        b_choose = (Button) findViewById(R.id.button);
        b_upload = (Button) findViewById(R.id.button2);
        b_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

            }
        });
        b_choose.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file_name=bitmaptofile(bitmap_chosen,"pp");
                Toast.makeText(getApplicationContext(),"path="+file_name.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                JsonHolder jsonHolder=retrofit.create(JsonHolder.class);
                RequestBody requestBody=RequestBody.create(MediaType.parse("image/*"),file_name);
                //MultipartBody.Part file=MultipartBody.Part.createFormData("file",file_name.getName(), requestBody);
                RequestBody body=RequestBody.create(MediaType.parse("text/plain"),"Ram");
                Call<Breed> call=jsonHolder.createBreed(requestBody,body);
                Toast.makeText(MainActivity.this,"yes",Toast.LENGTH_SHORT).show();
                call.enqueue(new Callback<Breed>() {
                    @Override
                    public void onResponse(Call<Breed> call, Response<Breed> response) {
                        Toast.makeText(MainActivity.this,"hi",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Breed> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"error="+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }));


    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageView=(ImageView)findViewById(R.id.imageView);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
             bitmap_chosen = null;
            try {

                bitmap_chosen = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bitmap_chosen);


            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public File bitmaptofile(Bitmap bitmap, String filename)
    {
        File file=null;
        try {
             file = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");


            file.mkdir();
            file.createNewFile();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,0,byteArrayOutputStream);
            byte[] bitmapdata=byteArrayOutputStream.toByteArray();
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(bitmapdata);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
           // return file;
        }
       return file;
    }

}