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
        imageList.add(new ImageModel("http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg", "Hannibal"));
        imageList.add(new ImageModel("http://tvfiles.alphacoders.com/100/hdclearart-10.png", "Big Bang Theory"));
        imageList.add(new ImageModel("http://cdn3.nflximg.net/images/3093/2043093.jpg", "House of Cards"));
        imageList.add(new ImageModel("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg", "Game of Thrones"));

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
                break;
        }
    }
}
