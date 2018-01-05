package net.kaunghtetlin.poc.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.kaunghtetlin.poc.R;
import net.kaunghtetlin.poc.data.vos.MovieVO;
import net.kaunghtetlin.poc.utils.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kaung Htet Lin on 11/10/2017.
 */

public class MovieViewHolder extends BaseViewHolder<MovieVO> {

    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;

    @BindView(R.id.iv_hero_image)
    ImageView ivHeroImage;

    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;

    @BindView(R.id.tv_movie_genre)
    TextView tvMovieGenre;

    @BindView(R.id.ll_popularity)
    LinearLayout llPopularity;

    private MovieVO mMovie;


    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(MovieVO data) {
        mMovie = data;
    }

    @Override
    public void bind(Context context) {
        if (mMovie != null) {
            if (mMovie.getOriginalTitle() != null)
                tvMovieTitle.setText(mMovie.getOriginalTitle());

            if (mMovie.getGenreIds() != null) {
                tvMovieGenre.setVisibility(View.VISIBLE);
                List<String> genreIdList = mMovie.getGenreIds();
                String genre = "( ";
                for (String genreId : genreIdList) {
                    genre += genreId + ", ";
                }
                genre = genre.substring(0, genre.length() - 2);
                genre += " )";
                tvMovieGenre.setText(genre);
            } else
                tvMovieGenre.setVisibility(View.GONE);

            if (mMovie.getPosterPath() != null) {
                Glide.with(context)
                        .load(AppConstants.IMAGE_BASE_PATH + mMovie.getPosterPath())
                        .into(ivHeroImage);
            }

            tvVoteAverage.setText(String.valueOf(mMovie.getVoteAverage()));

        }
    }
}
