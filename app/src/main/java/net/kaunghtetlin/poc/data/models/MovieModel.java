package net.kaunghtetlin.poc.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Movie;
import android.util.Log;

import net.kaunghtetlin.poc.MovieApp;
import net.kaunghtetlin.poc.data.vos.MovieVO;
import net.kaunghtetlin.poc.events.RestApiEvents;
import net.kaunghtetlin.poc.network.MoviesDataAgentImpl;
import net.kaunghtetlin.poc.persistance.MovieContract;
import net.kaunghtetlin.poc.utils.AppConstants;
import net.kaunghtetlin.poc.utils.ConfigUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kaung Htet Lin on 12/23/2017.
 */

public class MovieModel {

    private static MovieModel objInstance;

    private List<MovieVO> mMoive;
//    private int mMoviePageIndex = 1;

    private MovieModel() {
        EventBus.getDefault().register(this);
        mMoive = new ArrayList<>();
    }

    public static MovieModel getObjInstance() {
        if (objInstance == null) {
            objInstance = new MovieModel();
        }
        return objInstance;
    }

    public void startLoadingMovies(Context context) {
        MoviesDataAgentImpl.getObjInstance().loadMovies(AppConstants.ACCESS_TOKEN,
                ConfigUtils.getObjInstance().loadPageIndex(), context);
    }

    public List<MovieVO> getMovies() {
        if (mMoive == null) {
            return new ArrayList<>();
        }
        return mMoive;
    }

    public void loadMoreMovies(Context context) {
        MoviesDataAgentImpl.getObjInstance().loadMovies(AppConstants.ACCESS_TOKEN,
                ConfigUtils.getObjInstance().loadPageIndex(), context);
    }

    public void forceRefresMovie(Context context) {
        mMoive = new ArrayList<>();
//        mMoviePageIndex = 1;
        ConfigUtils.getObjInstance().savePageIndex(1);
        startLoadingMovies(context);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMovieDataLoaded(RestApiEvents.MovieDataLoadedEvent event) {

        mMoive.addAll(event.getLoadedMovies());
//        mMoviePageIndex = event.getLoadedPageIndex() + 1;
        ConfigUtils.getObjInstance().savePageIndex(event.getLoadedPageIndex()+1);

        // inserting data into mMovieSet
        ContentValues[] movieCVs = new ContentValues[event.getLoadedMovies().size()];
        List<ContentValues> genreCVList = new ArrayList<>();
        List<ContentValues> movieGenreCVList = new ArrayList<>();

        for (int index = 0; index < movieCVs.length; index++) {
            MovieVO movie = event.getLoadedMovies().get(index);
            movieCVs[index] = movie.parseToContentValues();

            if (movie.getGenreIds() != null) {
                for (String genreId : movie.getGenreIds()) {
                    ContentValues genreCV = new ContentValues();
                    genreCV.put(MovieContract.GenresEntry.COLUMN_GENRE_ID, genreId);
                    genreCV.put(MovieContract.GenresEntry.COLUMN_GENRE_NAME, genreId);
                    genreCVList.add(genreCV);

                    ContentValues movieGenreCV = new ContentValues();
                    movieGenreCV.put(MovieContract.MoviesGenresEntry.COLUMN_GENRE_ID, genreId);
                    movieGenreCV.put(MovieContract.MoviesGenresEntry.COLUMN_MOVIE_ID, movie.getMovieId());
                    movieGenreCVList.add(movieGenreCV);
                }
            }
        }

        int insertedRows = event.getContext().getContentResolver().bulkInsert(MovieContract.MoviesEntry.CONTENT_URI, movieCVs);
        Log.d(MovieApp.LOG_TAG, "Inserted Rows" + insertedRows);

        int insertedGenre = event.getContext().getContentResolver().bulkInsert(MovieContract.GenresEntry.CONTENT_URI,
                genreCVList.toArray(new ContentValues[0]));
        Log.d(MovieApp.LOG_TAG, "Inserted Rows" + insertedGenre);

        int insertedGenreMovie = event.getContext().getContentResolver().bulkInsert(MovieContract.MoviesGenresEntry.CONTENT_URI,
                movieGenreCVList.toArray(new ContentValues[0]));
        Log.d(MovieApp.LOG_TAG, "Inserted Rows" + insertedGenreMovie);

    }

}
