package net.kaunghtetlin.poc.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kaung Htet Lin on 12/23/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "movies.db";

    private static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MoviesEntry.TABLE_NAME + " (" +
            MovieContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.MoviesEntry.COLUMN_VOTE_COUNT + " INTEGER, " +
            MovieContract.MoviesEntry.COLUMN_MOVIE_ID + " TEXT, " +
            MovieContract.MoviesEntry.COLUMN_IS_VIDEO + " BOOLEAN, " +
            MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE + " DOUBLE, " +
            MovieContract.MoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            MovieContract.MoviesEntry.COLUMN_POPULARITY + " DOUBLE, " +
            MovieContract.MoviesEntry.COLUMN_POSTER_PATH + " TEXT, " +
            MovieContract.MoviesEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
            MovieContract.MoviesEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
            MovieContract.MoviesEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
            MovieContract.MoviesEntry.COLUMN_IS_ADULT + " BOOLEAN, " +
            MovieContract.MoviesEntry.COLUMN_OVERVIEW + " TEXT, " +
            MovieContract.MoviesEntry.COLUMN_RELEASE_DATE + " TEXT, " +

            " UNIQUE (" + MovieContract.MoviesEntry.COLUMN_MOVIE_ID + ") ON CONFLICT IGNORE, " +
            " UNIQUE (" + MovieContract.MoviesEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_GENRE_TABLE = "CREATE TABLE " + MovieContract.GenresEntry.TABLE_NAME + " (" +
            MovieContract.GenresEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.GenresEntry.COLUMN_GENRE_ID + " TEXT, " +
            MovieContract.GenresEntry.COLUMN_GENRE_NAME + " TEXT, " +

            " UNIQUE (" + MovieContract.GenresEntry.COLUMN_GENRE_ID + ") ON CONFLICT IGNORE, " +
            " UNIQUE (" + MovieContract.GenresEntry.COLUMN_GENRE_NAME + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_MOVIE_GENRE_TABLE = "CREATE TABLE " + MovieContract.MoviesGenresEntry.TABLE_NAME + " (" +
            MovieContract.MoviesGenresEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.MoviesGenresEntry.COLUMN_GENRE_ID + " TEXT, " +
            MovieContract.MoviesGenresEntry.COLUMN_MOVIE_ID + " TEXT" +

            " );";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_GENRE_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_GENRE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MoviesGenresEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.GenresEntry.TABLE_NAME);

        onCreate(db);
    }
}
