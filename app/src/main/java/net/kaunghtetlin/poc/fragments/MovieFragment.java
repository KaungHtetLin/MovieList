package net.kaunghtetlin.poc.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.kaunghtetlin.poc.MovieApp;
import net.kaunghtetlin.poc.R;
import net.kaunghtetlin.poc.adapters.MovieAdapter;
import net.kaunghtetlin.poc.components.EmptyViewPod;
import net.kaunghtetlin.poc.components.SmartRecyclerView;
import net.kaunghtetlin.poc.components.SmartScrollListener;
import net.kaunghtetlin.poc.data.models.MovieModel;
import net.kaunghtetlin.poc.data.vos.MovieVO;
import net.kaunghtetlin.poc.events.RestApiEvents;
import net.kaunghtetlin.poc.persistance.MovieContract;
import net.kaunghtetlin.poc.utils.AppConstants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kaung Htet Lin on 11/11/2017.
 */

public class MovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_movie)
    SmartRecyclerView rvMovie;

    @BindView(R.id.vp_empty_movie)
    EmptyViewPod vpEmptyMovie;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private SmartScrollListener mSmartScrollListener;

    private MovieAdapter mMovieAdapter;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);

        rvMovie.setEmptyView(vpEmptyMovie);
        rvMovie.setLayoutManager(new LinearLayoutManager(MovieApp.getContext(),
                LinearLayoutManager.VERTICAL, false));

        mMovieAdapter = new MovieAdapter(getContext());
        rvMovie.setAdapter(mMovieAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {
                Snackbar.make(rvMovie, "This is all the data for NOW.", Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(true);
                MovieModel.getObjInstance().loadMoreMovies(getContext());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MovieModel.getObjInstance().forceRefresMovie(getContext());
            }
        });

        rvMovie.addOnScrollListener(mSmartScrollListener);

        /*rvNews.setEmptyView(vpEmptyNews);
        rvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        newsAdapter = new NewsAdapter(getApplicationContext(), this);
        rvNews.setAdapter(newsAdapter);
*/
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(AppConstants.MOVIE_LIST_LOADER, null, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        List<MovieVO> movieList = MovieModel.getObjInstance().getMovies();
        if (!movieList.isEmpty()) {
            mMovieAdapter.setNewData(movieList);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvMovie, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                MovieContract.MoviesEntry.CONTENT_URI,
                null,
                null, null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<MovieVO> movieList = new ArrayList<>();

            do {
                MovieVO news = MovieVO.parseFromCursor(getContext(), data);
                movieList.add(news);
            } while (data.moveToNext());

            mMovieAdapter.setNewData(movieList);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
