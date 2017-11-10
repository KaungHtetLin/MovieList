package net.kaunghtetlin.poc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.kaunghtetlin.poc.R;
import net.kaunghtetlin.poc.viewholders.MovieViewHolder;

/**
 * Created by Kaung Htet Lin on 11/10/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflator = LayoutInflater.from(parent.getContext());
        View pocScreenView = mLayoutInflator.inflate(R.layout.view_item_movie, parent, false);
        return new MovieViewHolder(pocScreenView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 16;
    }
}
