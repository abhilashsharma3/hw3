package com.example.rkjc.news_app_2;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    ArrayList<NewsItem>  newsIte;
    NewsItemViewModel mNewsItemViewModel;
    int intervals = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.news_recyclerview);
        defineViewsandsetAdapter();
        getNewsDataandObserveWhenLoad();
        startFirebaseJobService();
    }

    public void populateRecyclerView(String jstring) {
        mNewsItemViewModel.getAllNewsitem().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable final List<NewsItem> newsItems) {
                // Update the cached copy of the words in the adapter.
               newsRecyclerViewAdapter.setNews((ArrayList<NewsItem>) newsItems);
            }
    });

    }
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int newsclicked=item.getItemId();
        if(newsclicked==R.id.action_search){
            getNewsDataandObserveWhenLoad();
        }
        if (newsclicked==R.menu.main_menu){
            getNewsDataandObserveWhenLoad();
        }
        return super.onOptionsItemSelected(item);
    }

    private void  defineViewsandsetAdapter() {
        newsRecyclerViewAdapter=new NewsRecyclerViewAdapter(this,newsIte);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(newsRecyclerViewAdapter);
        mNewsItemViewModel= ViewModelProviders.of(this).get(NewsItemViewModel.class);
    }

    public void getNewsDataandObserveWhenLoad() {
        mNewsItemViewModel.insert();
        mNewsItemViewModel.getAllNewsitem().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
          //      newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(this,new ArrayList<NewsItem>(newsItems));
               // newsIte=NewsItemRepository.insertAsyncTask.newsItems;
                newsRecyclerViewAdapter.setNews((ArrayList<NewsItem>)newsItems);
            }
        });
    }

    public void startFirebaseJobService(){
        FirebaseJobDispatcher dispatcher=new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));
            Job constraintRefreshJob = dispatcher.newJobBuilder()
                    .setService(FireBaseJobService.class)
                    .setLifetime(Lifetime.FOREVER)
                    .setRecurring(true)
                    .setTag("firebasejobservice")
                    .setTrigger(Trigger.executionWindow(intervals, intervals + 10))
                    .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                    .setReplaceCurrent(false)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .build();
            dispatcher.schedule(constraintRefreshJob);
    }
}

