package com.example.admin.flickr.feed;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class CharacterItemDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public CharacterItemDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        GridLayoutManager.LayoutParams layoutParams =(GridLayoutManager.LayoutParams) view.getLayoutParams();

        if (layoutParams.getSpanIndex()%2 == 0){
            outRect.top =offset;
            outRect.left =offset;
            outRect.right=offset/2;
        } else {
            outRect.top =offset;
            outRect.left =offset/2;
            outRect.right=offset;
        }
    }
}
