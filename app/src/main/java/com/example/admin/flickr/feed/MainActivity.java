package com.example.admin.flickr.feed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.admin.flickr.R;
import com.example.admin.flickr.models.PhotoItem;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity implements RecyleListener {

    private FragmentManager supportFragmentManagerrtFM;
    private FragmentTransaction fragmentTransaction;
    private MainFragment mainFragment;
    private ImageFullScreenFragment imageFullScreenFragment;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteiner);

        if (savedInstanceState == null) {
            supportFragmentManagerrtFM = getSupportFragmentManager();
            fragmentTransaction = supportFragmentManagerrtFM.beginTransaction();
            mainFragment = MainFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.add(R.id.fragment_container, mainFragment);
            fragmentTransaction.commit();
        }
        //Observable <String> observable = Observable.just("hello");
       // observable.subscribe();



    }


    @Override
    public PhotoItem onClick(PhotoItem item) {
/*        if (mainFragment != null) {
            mainFragment.onClick(item);
        }*/
        supportFragmentManagerrtFM = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManagerrtFM.beginTransaction();
        imageFullScreenFragment = ImageFullScreenFragment.newInstance(item);
        fragmentTransaction.add(R.id.fragment_container, imageFullScreenFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        return null;
    }
}
