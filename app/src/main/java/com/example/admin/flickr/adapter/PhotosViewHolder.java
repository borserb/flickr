package com.example.admin.flickr.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.flickr.R;
import com.example.admin.flickr.models.PhotoItem;

class PhotosViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView;
    private ImageView imageView;


    public PhotosViewHolder(@NonNull View itemView) {
        super(itemView);
        //nameTextView = itemView.findViewById(R.id.photos_tv_id);
        imageView = itemView.findViewById(R.id.image_view);
    }


    public void bind(PhotoItem photoItem) {
//        "https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg";
        String url = String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg", photoItem.getFarm(), photoItem.getServer(), photoItem.getId(), photoItem.getSecret());

        Glide.with(imageView)
                .load(url)
                .into(imageView);


    }
}
