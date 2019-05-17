package com.example.admin.flickr.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.flickr.PhotoItem;
import com.example.admin.flickr.R;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter <PhotosAdapter.PhotosViewHolder> {
    private List <PhotoItem> photoItems = new ArrayList <>();


    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photos_view, parent, false);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder holder, int position) {
        holder.nameTextView.setText(photoItems.get(position).getTitle());
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



    class PhotosViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;

        public PhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.photos_tv_id);
        }
    }


}
