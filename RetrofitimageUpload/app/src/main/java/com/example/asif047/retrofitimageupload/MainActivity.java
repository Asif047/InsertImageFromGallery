package com.example.asif047.retrofitimageupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText image_title;
    private Button chooseBtn,uploadBtn;
    private ImageView img;
    private static final int IMG_REQUEST=777;

    private Bitmap bitmap;

    private ImageClass imageClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        image_title= (EditText) findViewById(R.id.img_title);
        chooseBtn= (Button) findViewById(R.id.choose_bn);
        uploadBtn= (Button) findViewById(R.id.upload_bn);
        img= (ImageView) findViewById(R.id.imageview);

        chooseBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.choose_bn:
                selectImage();
                break;
            case R.id.upload_bn:

                uploadImage();
                break;
        }

    }



    private void uploadImage()
    {
        String Image=imageToString();
        String Title=image_title.getText().toString();
        ApiInterface apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageClass> call=apiInterface.UploadImage(Title,Image);
        Toast.makeText(MainActivity.this,Image,Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {
                //Toast.makeText(MainActivity.this,"Hello "+imageClass.getResponse(),Toast.LENGTH_LONG).show();
                imageClass=response.body();
                Toast.makeText(MainActivity.this,"Server Response: "+imageClass.getResponse(),Toast.LENGTH_LONG).show();
                img.setVisibility(View.GONE);
                image_title.setVisibility(View.GONE);
                chooseBtn.setEnabled(true);
                uploadBtn.setEnabled(false);
                image_title.setText("");
            }

            @Override
            public void onFailure(Call<ImageClass> call, Throwable t) {
                //Toast.makeText(MainActivity.this,"Server Response: "+t.getMessage().toString(),Toast.LENGTH_LONG).show();
                Log.e("Server Response: ", t.getMessage().toString());
                Log.e("Server Response: ", t.getStackTrace().toString());
            }
        });

    }



    private void selectImage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==IMG_REQUEST&&resultCode==RESULT_OK&&data!=null)
        {
            Uri path=data.getData();

            try
            {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                img.setImageBitmap(bitmap);
                img.setVisibility(View.VISIBLE);
                image_title.setVisibility(View.VISIBLE);
                chooseBtn.setEnabled(false);
                uploadBtn.setEnabled(true);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }


    private String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }



}
