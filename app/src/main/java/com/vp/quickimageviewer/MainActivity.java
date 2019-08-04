package com.vp.quickimageviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);



    }

    public void showImages(){

        final ArrayList<ImageModel> imageList = new ArrayList<>();
        imageList.add(new ImageModel("http://u01.appmifile.com/images/2016/10/21/c31c157f-97a2-479c-a2a3-74baf9790bb9.jpg", "Himangi Patel"));
        imageList.add(new ImageModel("https://nebula.wsimg.com/12e33523b6e7341bb7045fa321cdd463?AccessKeyId=63190F15169737A11884&disposition=0&alloworigin=1", ""));
        imageList.add(new ImageModel("http://avantgallery.com/wp-content/uploads/2016/02/Nick-Veasey-Selfie-23x29.5.jpg", ""));
        imageList.add(new ImageModel("https://cdn.pixabay.com/photo/2016/09/30/09/52/x-ray-1704855_960_720.jpg", "Main Activity"));
        imageList.add(new ImageModel("http://cdn.emgn.com/wp-content/uploads/2015/08/X-ray-Balarina.jpg", "Image Preview screen demo"));
        imageList.add(new ImageModel("https://cdn.pixabay.com/photo/2016/09/30/09/52/x-ray-1704855_960_720.jpg", ""));


        Intent intent = new Intent(MainActivity.this, ImageViewerActivity.class);
        intent.putExtra(ImageViewerActivity.IMAGE_LIST, imageList);
        intent.putExtra(ImageViewerActivity.CURRENT_ITEM, 0);
        intent.putExtra(ImageViewerActivity.SHOW_SAVE, "false");
        intent.putExtra(ImageViewerActivity.SHOW_SHARE, "false");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                showImages();
        }
    }
}
