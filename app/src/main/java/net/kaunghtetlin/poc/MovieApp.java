package net.kaunghtetlin.poc;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import net.kaunghtetlin.poc.dagger.AppComponent;
import net.kaunghtetlin.poc.dagger.AppModule;
import net.kaunghtetlin.poc.dagger.DaggerAppComponent;
import net.kaunghtetlin.poc.data.models.MovieModel;
import net.kaunghtetlin.poc.utils.ConfigUtils;

import javax.inject.Inject;

/**
 * Created by Kaung Htet Lin on 11/10/2017.
 */

public class MovieApp extends Application {

    public static final String LOG_TAG = "MovieApp";

    private AppComponent mAppComponent;

    @Inject
    Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = initDagger();       //dagger init
        mAppComponent.inject(this);     //register consumer


        ConfigUtils.initConfigUtils(getApplicationContext());
        MovieModel.getObjInstance().startLoadingMovies(getApplicationContext());

        Log.d(LOG_TAG, "mContext" + mContext);
    }

    private AppComponent initDagger() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
