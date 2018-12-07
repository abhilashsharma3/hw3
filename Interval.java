package com.example.rkjc.news_app_2;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class Interval {
    private static final int SECONDS=10;
    private static boolean sinit;
    private static final String TAG="news_tag";
    synchronized public static void interval(@NonNull final Context context){
        if(sinit){
            return;
        }
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsService.class).setTag(TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SECONDS,SECONDS*2))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintRefreshJob);
        sinit = true;
    }
}
