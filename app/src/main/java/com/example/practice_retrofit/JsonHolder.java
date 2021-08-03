package com.example.practice_retrofit;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface JsonHolder {
    @Multipart
    @POST("upload")
     Call<Breed> createBreed(  @Part MultipartBody.Part file,  @Part("sub_id") RequestBody sub_id);
}
