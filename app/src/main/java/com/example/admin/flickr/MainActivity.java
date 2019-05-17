package com.example.admin.flickr;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.admin.flickr.adapter.PhotosAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "9289d82268ddf830bbd685d1b01d9986";
    public static final String SECRET = "cfbe0cb24a2a15fd";
    private Handler handler;
    Executor executor = Executors.newSingleThreadExecutor();
    TextView tv;
    private Runnable showResult;
    private RecyclerView photosNameRV;
    private PhotosAdapter photosAdapter;
    private DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        handler = new Handler(Looper.getMainLooper());

        //getPhotosViaHttpUrlConnection();
        // getPhotosViaOkHttp();
        getPhotosViaRetrofit();



    }

    private void initRecyclerView() {
        photosNameRV = findViewById(R.id.recycleView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        photosNameRV.setLayoutManager(layoutManager);
        photosAdapter = new PhotosAdapter();

        mDividerItemDecoration = new DividerItemDecoration(photosNameRV.getContext(), layoutManager.getOrientation());
        photosNameRV.addItemDecoration(new CharacterItemDecoration(50));
        /*photosNameRV.addItemDecoration(mDividerItemDecoration);*/

        photosNameRV.setAdapter(photosAdapter);

    }

    public interface GitHubService {
        @GET("services/rest/")
        retrofit2.Call <Result> repos(
                @Query("method") String method,
                @Query("api_key") String api_key,
                @Query("format") String format,
                @Query("nojsoncallback") int noJsonCallback);
    }

    private void getPhotosViaRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService gitHubService = retrofit.create(GitHubService.class);

        gitHubService.repos("flickr.photos.getRecent", API_KEY, "json", 1).enqueue(new Callback <Result>() {
            @Override
            public void onResponse(Call <Result> call, Response <Result> response) {

                StringBuilder builder = new StringBuilder();
                List<PhotoItem> photoItems = response.body().getPhotos().getPhoto();

          /*      for (int i=0;i<photoItems.size();i++){
                    builder.append(photoItems.get(i).getTitle());
                }*/
                initRecyclerView();
                photosAdapter.setItems(photoItems);


                // List<PhotoItem> photoItems=(Result)response.body();
                // photoItems =(PhotoItem)response.body().getClass(PhotoItem.class);

/*                final String titles = builder.toString();
                tv.setText(titles);*/


            }

            @Override
            public void onFailure(Call <Result> call, Throwable t) {

            }
        });


    }

    private void getPhotosViaOkHttp() {
        final OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://api.flickr.com/services/rest/" +
                        "?method=flickr.photos.getRecent&" +
                        "api_key=" + API_KEY + "&" +
                        "format=json&" +
                        "nojsoncallback=1")
                .get()
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {


            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                ResponseBody body = response.body();

                Gson gson = new Gson();
                Result result = gson.fromJson(new String(body.bytes()), Result.class);

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < 3 && i < result.getPhotos().getPhoto().size(); i++) {
                    builder.append("   " + result.getPhotos().getPhoto().get(i).getTitle());
                }
                final String titles = builder.toString();

                showResult = new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(titles);
                    }
                };
                handler.post(showResult);


            }

        });


    }


    private void getPhotosViaHttpUrlConnection() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.flickr.com/services/rest/" +
                            "?method=flickr.photos.getRecent&" +
                            "api_key=" + API_KEY + "&" +
                            "format=json&" +
                            "nojsoncallback=1");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    final String jsonString = getStringFromInputString(inputStream);

/*                    JSONObject jsonObject = new JSONObject(result);
                    final String total = jsonObject.getJSONObject("photos").getInt("total")+"";*/
                    Gson gson = new Gson();
                    Result result = gson.fromJson(jsonString, Result.class);

                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 3 && i < result.getPhotos().getPhoto().size(); i++) {
                        builder.append("   " + result.getPhotos().getPhoto().get(i).getTitle());
                    }
                    final String titles = builder.toString();


                    showResult = new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(titles);
                        }
                    };
                    handler.post(showResult);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    private String getStringFromInputString(InputStream stream) throws IOException {
        int n = 0;
        char buffer[] = new char[1024 * 4];
        InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF8");
        StringWriter stringWriter = new StringWriter();
        while (-1 != (n = inputStreamReader.read(buffer))) stringWriter.write(buffer, 0, n);
        return stringWriter.toString();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (showResult != null) {
            handler.removeCallbacks(showResult);
        }
    }
}
