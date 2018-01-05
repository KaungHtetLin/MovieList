package net.kaunghtetlin.poc.events;

import android.content.Context;

import net.kaunghtetlin.poc.data.vos.MovieVO;

import java.util.List;

/**
 * Created by Kaung Htet Lin on 12/23/2017.
 */

public class RestApiEvents {

    public static class EmptyResponseEvent {
        // errors when response is empty
    }

    public static class ErrorInvokingAPIEvent {
        // errors when invoking API

        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class MovieDataLoadedEvent {
        // when data is loaded

        private int loadedPageIndex;
        private List<MovieVO> loadedMovies;
        private Context context;

        public MovieDataLoadedEvent(int loadedPageIndex, List<MovieVO> loadedMovies, Context context) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedMovies = loadedMovies;
            this.context = context;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public List<MovieVO> getLoadedMovies() {
            return loadedMovies;
        }

        public Context getContext() {
            return context;
        }
    }


}
