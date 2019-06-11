package com.example.admin.flickr.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.flickr.App;
import com.example.admin.flickr.R;
import com.example.admin.flickr.adapter.PhotosAdapter;
import com.example.admin.flickr.models.PhotoItem;
import com.example.admin.flickr.models.Result;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import rx.schedulers.Schedulers;
;

public class MainFragment extends Fragment {
    private RecyclerView photosNameRV;
    private PhotosAdapter photosAdapter;
    private DividerItemDecoration mDividerItemDecoration;

    private List<PhotoItem> photoItems;
    private View view = null;
    Disposable disposable;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "View = "+v.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        getPhotosViaRetrofit();
    }

    private void initRecyclerView() {
        photosNameRV = view.findViewById(R.id.recycleView);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        photosNameRV.setLayoutManager(layoutManager);
        mDividerItemDecoration = new DividerItemDecoration(photosNameRV.getContext(), layoutManager.getOrientation());
        photosNameRV.addItemDecoration(new CharacterItemDecoration(50));
        photosAdapter = new PhotosAdapter(getContext(), photoItems);
        photosNameRV.setAdapter(photosAdapter);
    }

    private void getPhotosViaRetrofit() {
        App app = App.getApp(getActivity());
        disposable = app.getAPI().repos("flickr.photos.getRecent", FlickrApi.API_KEY, "json", 1)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io()) //поток исполнения
                .observeOn(AndroidSchedulers.mainThread()) //поток получения результат
                .subscribe(result -> {
                    //StringBuilder builder = new StringBuilder();
                    photoItems = result.getPhotos().getPhoto();
                    initRecyclerView();
                }, throwable -> Log.i("RxLearning", throwable.toString())
                );

        // callConnection = app.getAPI().repos("flickr.photos.getRecent", FlickrApi.API_KEY, "json", 1);

/*        callConnection.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call <Result> call, Response<Result> response) {
                StringBuilder builder = new StringBuilder();
                photoItems = response.body().getPhotos().getPhoto();
                initRecyclerView();
            }*/
/*
            @Override
            public void onFailure(Call <Result> call, Throwable t) {
            }
        });*/


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable!=null){
            disposable.dispose();
    }

    }


    public void onClick(PhotoItem item) {
        Toast.makeText(getActivity(), "Recycle - Activity - Main Fragment", Toast.LENGTH_SHORT).show();
        ImageFullScreenFragment imageFullScreenFragment = ImageFullScreenFragment.newInstance(item);
        imageFullScreenFragment.show(getChildFragmentManager(),ImageFullScreenFragment.TAG);
    }
}
