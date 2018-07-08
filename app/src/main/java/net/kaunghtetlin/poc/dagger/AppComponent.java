package net.kaunghtetlin.poc.dagger;

import net.kaunghtetlin.poc.MovieApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kaung Htet Lin on 1/14/2018.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(MovieApp app);

}
