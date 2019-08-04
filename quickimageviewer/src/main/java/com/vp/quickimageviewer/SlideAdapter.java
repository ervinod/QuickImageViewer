package com.vp.quickimageviewer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private List<ImageModel> list;
    private OnItemClickListener<ImageModel> listener;

    public SlideAdapter(Context context, List<ImageModel> list, OnItemClickListener<ImageModel> listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.item_preview, container, false);

        final ProgressBar progressBar = view.findViewById(R.id.imageProgressBar);
        PhotoView image = view.findViewById(R.id.iv_preview);
        final TextView tvImageDescription = view.findViewById(R.id.tvImageDescription);

//        Glide.with(context)
//                .load(list.get(position).getImageURL())
//                .thumbnail(Glide.with(context).load(R.raw.image_loader).thumbnail(0.2f))
//                .into(image);

        Glide.with(context)
                .load(list.get(position).getImageURL())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("IMAGE", "failed to load image");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("IMAGE", "image successfully loaded");
                        return false;
                    }
                })
                .into(image);


        if (list.get(position).getImageDescription().isEmpty()) {
            tvImageDescription.setVisibility(View.GONE);
        } else {
            tvImageDescription.setVisibility(View.VISIBLE);
            tvImageDescription.setText(list.get(position).getImageDescription());
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(list.get(position));
                if (!list.get(position).getImageDescription().isEmpty()) {
                    tvImageDescription.setVisibility(tvImageDescription.getVisibility() == View.VISIBLE ?
                            View.INVISIBLE : View.VISIBLE);
                }
            }
        });

        container.addView(view);
        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}