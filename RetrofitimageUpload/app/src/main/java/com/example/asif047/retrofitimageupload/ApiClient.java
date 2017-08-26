package com.example.asif047.retrofitimageupload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Asif on 8/26/2017.
 */

public class ApiClient {


    private static final String BASE_URL="http://192.168.56.1/imageupload/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient()
    {
        if(retrofit==null)
        {

            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

}
