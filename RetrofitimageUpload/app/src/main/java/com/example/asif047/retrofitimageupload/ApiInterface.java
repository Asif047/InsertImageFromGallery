package com.example.asif047.retrofitimageupload;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Asif on 8/26/2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("upload.php")
    Call<ImageClass>UploadImage(@Field("title")String title, @Field("image")String image);
}
