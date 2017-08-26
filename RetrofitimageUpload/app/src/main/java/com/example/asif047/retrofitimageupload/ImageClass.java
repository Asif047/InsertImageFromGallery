package com.example.asif047.retrofitimageupload;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Asif on 8/26/2017.
 */

public class ImageClass {



    @SerializedName("title")
    private String Title;

    @SerializedName("image")
    private String Image;

    @SerializedName("response")
    private String Response;

    public String getResponse() {
        return Response;
    }
}
