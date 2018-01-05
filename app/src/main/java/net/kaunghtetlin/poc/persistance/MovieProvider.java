package net.kaunghtetlin.poc.persistance;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Kaung Htet Lin on 12/23/2017.
 */

public class MovieProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int GENERS = 200;
    public static final int MOVIES_GENERS = 300;

    private static final UriMatcher sUriMatcher = buildUriMather();
    private MovieDBHelper mDBHelper;

    private static UriMatcher buildUriMather() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_GENRES, GENERS);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES_GENRES, MOVIES_GENERS);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                return MovieContract.MoviesEntry.TABLE_NAME;
            case GENERS:
                return MovieContract.GenresEntry.TABLE_NAME;
            case MOVIES_GENERS:
                return MovieContract.MoviesGenresEntry.TABLE_NAME;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    private Uri getContentUri(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                return MovieContract.MoviesEntry.CONTENT_URI;
            case GENERS:
                return MovieContract.GenresEntry.CONTENT_URI;
            case MOVIES_GENERS:
                return MovieContract.MoviesGenresEntry.CONTENT_URI;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor queryCursor = mDBHelper.getReadableDatabase().query(getTableName(uri),
                projection,
                selection,
                selectionArgs,
                null, //group_by
                null, //having
                sortOrder);

        if (getContext() != null) {
            queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                return MovieContract.MoviesEntry.DIR_TYPE;
            case GENERS:
                return MovieContract.GenresEntry.DIR_TYPE;
            case MOVIES_GENERS:
                return MovieContract.MoviesGenresEntry.DIR_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        long _id = db.insert(tableName, null, contentValues);

        if (_id > 0) {
            Uri tableContentUri = getContentUri(uri);
            Uri insertedUri = ContentUris.withAppendedId(tableContentUri, _id);

            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();

        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();

//            db.close();

        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, contentValues, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }
}
