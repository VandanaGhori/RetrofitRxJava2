package com.example.retrofitrxjava2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.retrofitrxjava2.Adapter.PostAdapter;
import com.example.retrofitrxjava2.Model.Post;
import com.example.retrofitrxjava2.Retrofit.IMyAPI;
import com.example.retrofitrxjava2.Retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    // Fetch data from API
    IMyAPI myAPI;
    RecyclerView recyclerView_posts;
    PostAdapter postAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init API
        Retrofit retrofit = RetrofitClient.getSingleInstance();
        myAPI = retrofit.create(IMyAPI.class);

        // View
        recyclerView_posts = (RecyclerView) findViewById(R.id.recycler_posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_posts.setLayoutManager(layoutManager);
        //recyclerView_posts.setHasFixedSize(true);

        fetchData();
    }

    // fetch Data from API and set data for RecyclerView
    private void fetchData() {
        compositeDisposable.add(myAPI.getPosts().
                subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Post>>() {
            @Override
            public void accept(List<Post> posts) throws Exception {
                displayData(posts);
            }
        }));
    }

    private void displayData(List<Post> posts) {
        postAdapter = new PostAdapter(this, posts);
        recyclerView_posts.setAdapter(postAdapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}