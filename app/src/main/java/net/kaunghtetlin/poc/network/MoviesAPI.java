package net.kaunghtetlin.poc.network;

import net.kaunghtetlin.poc.network.responses.GetMoviesResponse;
import net.kaunghtetlin.poc.utils.AppConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Kaung Htet Lin on 12/23/2017.
 */

public interface MoviesAPI {

    @FormUrlEncoded
    @POST("v1/getPopularMovies.php")
    Call<GetMoviesResponse> loadPopularMovies(
            @Field("access_token") String accessToken,
            @Field("page") int pageIndex);
}
