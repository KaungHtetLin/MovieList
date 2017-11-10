package net.kaunghtetlin.poc.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.kaunghtetlin.poc.MovieApp;
import net.kaunghtetlin.poc.R;
import net.kaunghtetlin.poc.adapters.MovieAdapter;

/**
 * Created by Kaung Htet Lin on 11/11/2017.
 */

public class MovieFragment extends Fragment{

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);


        RecyclerView rvMovie=view.findViewById(R.id.rv_movie);

        rvMovie.setLayoutManager(new LinearLayoutManager(MovieApp.getContext(),LinearLayoutManager.VERTICAL,false));

        MovieAdapter movieAdapter=new MovieAdapter();
        rvMovie.setAdapter(movieAdapter);

        return view;
    }
}
