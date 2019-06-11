package com.example.admin.flickr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.flickr.feed.ImageFullScreenFragment;
import com.example.admin.flickr.feed.RecyleListener;
import com.example.admin.flickr.models.PhotoItem;
import com.example.admin.flickr.R;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter <PhotosViewHolder> {
    private List <PhotoItem> photoItems = new ArrayList <>();
    Context context;
    RecyleListener recyleListener;

    public PhotosAdapter(Context context, List<PhotoItem> photoItems) {
        this.photoItems = photoItems;
        this.context = context;
    }

    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photos_view, parent, false);
        final PhotosViewHolder photosViewHolder = new PhotosViewHolder(view);

        if (context instanceof RecyleListener){
            recyleListener = (RecyleListener) context;
        }
        photosViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = photosViewHolder.getAdapterPosition();
                recyleListener.onClick(photoItems.get(adapterPosition));

            }
        });
        return photosViewHolder;
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder holder, int position) {
        holder.bind(photoItems.get(position));

    }

    @Override
    public int getItemCount() {
        return photoItems.size();
    }

    public void setItems(List <PhotoItem> photoItems) {
        this.photoItems.addAll(photoItems);
        notifyDataSetChanged();
    }

    public void clearItems() {
        photoItems.clear();
        notifyDataSetChanged();
    }


}
