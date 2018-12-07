package com.example.rkjc.news_app_2;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


public class FireBaseJobService extends JobService {
    Intent intent;
    @Override
    public boolean onStartJob(final JobParameters job) {
        Log.d("JobStarted", "onStartJob: "+"test");
        Toast.makeText(this, "onStartJob", Toast.LENGTH_SHORT).show();
        intent =new Intent(getApplicationContext(), Intentservice.class);
        new NewsUpdatingTask(intent,job).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    public class NewsUpdatingTask extends AsyncTask<Void, Void, String> {

        Notifications showNotification;
        NewsItemRepository newsDataRepository;
        JobParameters job;
        Intent intent;

        NewsUpdatingTask(Intent intent,JobParameters job) {
            this.job = job;
            this.intent =intent;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(Void... voids) {
            Log.d("Job", "doInBackground: "+"test");
            showNotification = new Notifications(FireBaseJobService.this);
            showNotification.showNotification("NewsApp","News Updating...",intent);
            newsDataRepository = new NewsItemRepository(getApplication());
            newsDataRepository.getAllNewsitem();
            return "UpdateNews";
        }
        
        @Override
        protected void onPostExecute(String s) {
            jobFinished(job, false);
        }
    }
}
