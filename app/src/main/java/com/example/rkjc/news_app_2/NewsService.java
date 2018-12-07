package com.example.rkjc.news_app_2;

import android.app.Notification;
import android.app.job.JobParameters;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Build;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;

import java.io.File;

public class NewsService extends com.firebase.jobdispatcher.JobService
{
    AsyncTask masync;
    Intent intent;
    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        intent =new Intent(getApplicationContext(), Intentservice.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            new NewsUpdate(intent,(JobParameters)job).execute();
        }
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if (masync!=null){
            masync.cancel(false);
        }

        return true;
    }

    public class NewsUpdate extends AsyncTask<Void,Void,String>{
        Notifications notification;
        NewsItemRepository newsItemRepository;
        JobParameters jobParameters;
        Intent intent1;

        NewsUpdate(Intent intent,JobParameters jobParameters){
            this.intent1=intent;
            this.jobParameters=jobParameters;
        }
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            notification=new Notifications(NewsService.this);
            notification.showNotification("News App","News Updating",intent1);
            newsItemRepository=new NewsItemRepository(getApplication());
            newsItemRepository.insert();
            return "Updating";
        }
        @Override
        protected void onPostExecute(String s){
            jobFinished((com.firebase.jobdispatcher.JobParameters) jobParameters,false);
        }
    }
}
