package net.kaunghtetlin.poc.dagger;

import android.content.Context;

import net.kaunghtetlin.poc.MovieApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kaung Htet Lin on 1/14/2018.
 */

@Module
public class AppModule {

    private MovieApp mApp;

    public AppModule(MovieApp application) {
        mApp = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    /*@Provides
    @Singleton
    public MMNewsDataAgent provideMMNewsDataAgent() {
        return new MMNewsDataAgentImpl();
    }

    @Provides
    @Singleton
    public NewsModel provideNewsModel(Context context) {
        return new NewsModel(context);
    }

    @Provides
    public NewsListPresenter provideNewsListPresenter() {
        return new NewsListPresenter();
    }
*/
}