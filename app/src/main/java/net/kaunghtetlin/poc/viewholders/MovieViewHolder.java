package net.kaunghtetlin.poc.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.kaunghtetlin.poc.R;
import net.kaunghtetlin.poc.data.vos.MovieVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kaung Htet Lin on 11/10/2017.
 */

public class MovieViewHolder extends BaseViewHolder<MovieVO> {

    @BindView(R.id.tv_rating)
    TextView tvRating;

    @BindView(R.id.iv_hero_image)
    FrameLayout ivHeroImage;

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
            tvMovieTitle.setText(mMovie.getOriginalTitle());
        }
    }
}
