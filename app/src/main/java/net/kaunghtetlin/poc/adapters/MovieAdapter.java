package net.kaunghtetlin.poc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.kaunghtetlin.poc.R;
import net.kaunghtetlin.poc.data.vos.MovieVO;
import net.kaunghtetlin.poc.viewholders.MovieViewHolder;

/**
 * Created by Kaung Htet Lin on 11/10/2017.
 */

public class MovieAdapter extends BaseRecyclerAdapter<MovieViewHolder, MovieVO> {

    public MovieAdapter(Context context) {
        super(context);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View pocScreenView = mLayoutInflator.inflate(R.layout.view_item_movie, parent, false);
        return new MovieViewHolder(pocScreenView);
    }
}
