package net.kaunghtetlin.poc;

import android.app.Application;
import android.content.Context;

import net.kaunghtetlin.poc.data.models.MovieModel;

/**
 * Created by Kaung Htet Lin on 11/10/2017.
 */

public class MovieApp extends Application {

    public static final String LOG_TAG = "MovieApp";

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MovieModel.getObjInstance().startLoadingMovies(getApplicationContext());
    }
}
