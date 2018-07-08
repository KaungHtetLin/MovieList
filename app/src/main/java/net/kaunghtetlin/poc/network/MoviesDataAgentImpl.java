package net.kaunghtetlin.poc.network;

import android.content.Context;

import net.kaunghtetlin.poc.events.RestApiEvents;
import net.kaunghtetlin.poc.network.responses.GetMoviesResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kaung Htet Lin on 12/23/2017.
 */

public class MoviesDataAgentImpl implements MoviesDataAgent {

    private static MoviesDataAgentImpl objInstance;

    private MoviesAPI theAPI;

    private MoviesDataAgentImpl() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://padcmyanmar.com/padc-3/popular-movies/apis/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        theAPI = retrofit.create(MoviesAPI.class);
    }

    public static MoviesDataAgentImpl getObjInstance() {
        if (objInstance == null) {
            objInstance = new MoviesDataAgentImpl();
        }
        return objInstance;
    }

    @Override
    public void loadMovies(String accessToken, int page, final Context context) {
        Call<GetMoviesResponse> loadMovieCall = theAPI.loadPopularMovies(accessToken, page);
        loadMovieCall.enqueue(
                new MovieCallBack<GetMoviesResponse>() {
                    @Override
                    public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                        super.onResponse(call, response);
                        GetMoviesResponse getMoviesResponse = response.body();
                        if (getMoviesResponse != null && getMoviesResponse.getPopularMovies()!=null
                                && getMoviesResponse.getPopularMovies().size() > 0) {
                            RestApiEvents.MovieDataLoadedEvent movieDataLoadedEvent = new RestApiEvents.MovieDataLoadedEvent
                                    (getMoviesResponse.getPage(), getMoviesResponse.getPopularMovies(), context);
                            EventBus.getDefault().post(movieDataLoadedEvent);
                        }
                    }
                }
        );
    }

}
