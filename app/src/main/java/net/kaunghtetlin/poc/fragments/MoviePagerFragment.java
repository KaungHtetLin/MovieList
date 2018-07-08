package net.kaunghtetlin.poc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.kaunghtetlin.poc.R;
import net.kaunghtetlin.poc.adapters.MoviePagerAdapter;

/**
 * Created by Kaung Htet Lin on 11/11/2017.
 */

public class MoviePagerFragment extends Fragment {


    private MoviePagerAdapter mMoviePagerAdapter;

    public static MoviePagerFragment newInstance() {
        MoviePagerFragment fragment = new MoviePagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoviePagerAdapter = new MoviePagerAdapter(getActivity().getSupportFragmentManager());
        mMoviePagerAdapter.addTab(MovieFragment.newInstance(), "NOW ON CINEMA");
        mMoviePagerAdapter.addTab(MovieFragment.newInstance(), "UPCOMING");
        mMoviePagerAdapter.addTab(MovieFragment.newInstance(), "MOST POPULAR");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pager_movie, container, false);

        TabLayout tlMovie=rootView.findViewById(R.id.tl_movie);
        ViewPager pagerMovie=rootView.findViewById(R.id.pager_movie);

        pagerMovie.setAdapter(mMoviePagerAdapter);
        pagerMovie.setOffscreenPageLimit(mMoviePagerAdapter.getCount());
//        pagerMovie.setOff

        tlMovie.setupWithViewPager(pagerMovie);

        return rootView;
    }
}
