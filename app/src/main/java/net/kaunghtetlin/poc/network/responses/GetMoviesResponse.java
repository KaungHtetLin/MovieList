package net.kaunghtetlin.poc.network.responses;

import com.google.gson.annotations.SerializedName;

import net.kaunghtetlin.poc.data.vos.MovieVO;
import net.kaunghtetlin.poc.network.MovieResponse;

import java.util.List;

/**
 * Created by Kaung Htet Lin on 12/23/2017.
 */

public class GetMoviesResponse extends MovieResponse{

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("apiVersion")
    private String apiVersion;

    @SerializedName("page")
    private int page;

    @SerializedName("popular-movies")
    private List<MovieVO> popularMovies;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public int getPage() {
        return page;
    }

    public List<MovieVO> getPopularMovies() {
        return popularMovies;
    }

}
