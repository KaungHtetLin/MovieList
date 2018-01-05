package net.kaunghtetlin.poc.network;

import android.content.Context;

/**
 * Created by Kaung Htet Lin on 12/23/2017.
 */

public interface MoviesDataAgent {
    void loadMovies(String accessToken, int page, Context context);
}
