package com.example.admin.flickr.feed;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.flickr.R;
import com.example.admin.flickr.models.PhotoItem;


public class ImageFullScreenFragment extends DialogFragment {
    public static final String TAG = "ImageFullScreenFragment TAG";
    private static PhotoItem photoItem;
    private ImageView imageView;


    public ImageFullScreenFragment() {
    }



    public void setPhotoItems(PhotoItem photoItems) {
        this.photoItem = photoItems;
    }



    public static ImageFullScreenFragment newInstance(PhotoItem item) {
        ImageFullScreenFragment fragment = new ImageFullScreenFragment();
        photoItem = item;
              return fragment;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_full_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.fragment_two_image);

        setViewSize(setViewSize(imageView));

        String url = String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg",photoItem.getFarm(),photoItem.getServer(),photoItem.getId(),photoItem.getSecret());

        Glide.with(imageView)
                .load(url)
                .into(imageView);



    }

    private ImageView setViewSize(ImageView imageView) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth(); // ((display.getWidth()*20)/100)
        int height = display.getHeight();// ((display.getHeight()*30)/100)
        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width,height);
        imageView.setLayoutParams(parms);
        return imageView;
    }
}
