package net.kaunghtetlin.poc.mvp.presenters;

import android.content.Context;
import android.database.Cursor;

import net.kaunghtetlin.poc.data.models.MovieModel;
import net.kaunghtetlin.poc.data.vos.MovieVO;
import net.kaunghtetlin.poc.mvp.views.MovieView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaung Htet Lin on 1/14/2018.
 */

public class MoviePresenter extends BasePresenter<MovieView> {

    @Override
    public void onCreate(MovieView view) {
        super.onCreate(view);
    }

    @Override
    public void onStart() {
        List<MovieVO> movieList = MovieModel.getObjInstance().getMovies();
        if (!movieList.isEmpty()) {
            mView.displayMovieList(movieList);
        } else {
//            mView.showLoading();
        }

    }

    @Override
    public void onStop() {

    }


    public void onMovieListEndReach(Context context) {
        MovieModel.getObjInstance().loadMoreMovies(context);
    }

    public void onForceRefresh(Context context) {
        MovieModel.getObjInstance().forceRefresMovie(context);
    }

    public void onDataLoaded(Context context, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<MovieVO> movieList = new ArrayList<>();

            do {
                MovieVO movies = MovieVO.parseFromCursor(context, data);
                movieList.add(movies);
            } while (data.moveToNext());

            mView.displayMovieList(movieList);
        }
    }
}
