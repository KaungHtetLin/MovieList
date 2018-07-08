package net.kaunghtetlin.poc.mvp.views;

import net.kaunghtetlin.poc.data.vos.MovieVO;

import java.util.List;

/**
 * Created by Kaung Htet Lin on 1/14/2018.
 */

public interface MovieView {
    void displayMovieList(List<MovieVO> movieList);
    void showLoading();

}
