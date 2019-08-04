package com.vp.quickimageviewer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

public class ImageViewerActivity extends AppCompatActivity implements OnItemClickListener<ImageModel>, PullDownLayout.Callback {


    public static String IMAGE_LIST = "intent_image_item";
    public static String CURRENT_ITEM = "current_item";
    public static String SHOW_SAVE = "show_save";
    public static String SHOW_SHARE = "show_share";

    public Toolbar toolbar;
    ViewPager vPager;
    CustomViewPager customViewPager;
    private List<ImageModel> mUriList;
    private Bitmap mBitmap;
    private String view;
    private boolean showSave = true;
    private boolean showShare = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        customViewPager = new CustomViewPager(ImageViewerActivity.this);

        toolbar = findViewById(R.id.toolbar);
        vPager = findViewById(R.id.vPager);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        PullDownLayout pullDownLayout = findViewById(R.id.pull);
        pullDownLayout.setCallback(this);

        setUpViews();
    }




    private void setUpViews() {

        mUriList = (List<ImageModel>)
                getIntent().getSerializableExtra(IMAGE_LIST);
        String show_save = getIntent().getStringExtra(SHOW_SAVE);
        String show_share = getIntent().getStringExtra(SHOW_SHARE);

        if(show_save!=null && !show_save.equals("true")){
            showSave= false;
        }

        if(show_share!=null && !show_share.equals("true")){
            showShare= false;
        }

        SlideAdapter slideAdapter =
                new SlideAdapter(this, mUriList, this);
        vPager.setAdapter(slideAdapter);

        vPager.setCurrentItem(getIntent().getIntExtra(CURRENT_ITEM, 0));

        vPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float normalizedPosition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedPosition / 2 + 0.5f);
                page.setScaleY(normalizedPosition / 2 + 0.5f);
            }
        });
    }

    @Override
    public void onItemClick(ImageModel item) {
        toolbar.setVisibility(toolbar.getVisibility() == View.VISIBLE ?
                View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_share, menu);
        MenuItem save = menu.findItem(R.id.menu_save);
        MenuItem share = menu.findItem(R.id.menu_share);

        if(showSave){
            save.setVisible(true);
        }else{
            save.setVisible(false);
        }

        if(showShare){
            share.setVisible(true);
        }else{
            share.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            view = "Save";
            Glide.with(ImageViewerActivity.this)
                    .asBitmap()
                    .load(mUriList.get(vPager.getCurrentItem()).getImageURL())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, com.bumptech.glide.request.transition.Transition<? super Bitmap> transition) {
                            handleSavePermission(resource);
                        }

                    });
        } else if (item.getItemId() == R.id.menu_share) {
            view = "Share";
            Glide.with(ImageViewerActivity.this)
                    .asBitmap()
                    .load(mUriList.get(vPager.getCurrentItem()).getImageURL())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, com.bumptech.glide.request.transition.Transition<? super Bitmap> transition) {
                            handleSharePermission(resource);

                        }
                    });
        }
        return true;
    }

    private void handleSavePermission(Bitmap resource) {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CheckPermission.hasPermission(permission, ImageViewerActivity.this)) {
                Util.saveImageToGallery(ImageViewerActivity.this, resource);
            } else {
                mBitmap = resource;
                CheckPermission.requestPerm(new String[]{permission},
                        CheckPermission.PERMISSION_STORAGE, ImageViewerActivity.this);
            }
        } else {
            Util.saveImageToGallery(ImageViewerActivity.this, resource);
        }
    }

    private void handleSharePermission(Bitmap resource) {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CheckPermission.hasPermission(permission, ImageViewerActivity.this)) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Util.shareImage(ImageViewerActivity.this, resource));
                startActivity(Intent.createChooser(intent, "Share Image"));
            } else {
                mBitmap = resource;
                CheckPermission.requestPerm(new String[]{permission},
                        CheckPermission.PERMISSION_STORAGE, ImageViewerActivity.this);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, Util.shareImage(ImageViewerActivity.this, resource));
            startActivity(Intent.createChooser(intent, "Share Image"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CheckPermission.PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (view.equals("Save")) {
                    handleSavePermission(mBitmap);
                } else {
                    handleSharePermission(mBitmap);
                }
            } else {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        // user rejected the permission
                        boolean showRationale = shouldShowRequestPermissionRationale(permission);
                        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)
                                || Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)) {
                            if (!showRationale) {
                                showAlertDialog();
                            } else
                                shouldShowRequestPermissionRationale(permission);
                        }
                    }
                }
            }

        }

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(ImageViewerActivity.this).setPositiveButton("GO TO SETTING",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("DENY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Permission denied")
                        .setMessage("Without storage permission the app" +
                                " is unable to open gallery or to save photos." +
                                " Are you sure want to deny this permission?");
        builder.create().show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onPullStart() {

    }

    @Override
    public void onPull(float progress) {

    }

    @Override
    public void onPullCancel() {

    }

    @Override
    public void onPullComplete() {
        finish();
    }
}

