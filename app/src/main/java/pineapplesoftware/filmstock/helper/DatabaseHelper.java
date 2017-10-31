package pineapplesoftware.filmstock.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import pineapplesoftware.filmstock.model.dto.Movie;

/**
 * Created by Higor Ernandes on 2017-10-30.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    //region Attributes

    private static final int DATABASE_VERSION = 1;
    private static final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

    private static final String DATABASE_NAME = "moviesManager";

    private static final String TABLE_MOVIES = "movies";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_YEAR = "year";
    private static final String KEY_RATED = "rated";
    private static final String KEY_RELEASED = "released";
    private static final String KEY_RUNTIME = "runtime";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_DIRECTOR = "director";
    private static final String KEY_WRITER = "writer";
    private static final String KEY_ACTORS = "actors";
    private static final String KEY_PLOT = "plot";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_AWARDS = "awards";
    private static final String KEY_POSTER_URL = "poster_url";
    private static final String KEY_METASCORE = "metascore";
    private static final String KEY_IMDB_RATING = "imdb_rating";
    private static final String KEY_IMDB_VOTES = "imdb_votes";
    private static final String KEY_IMDB_ID = "imdb_id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DVD = "dvd";
    private static final String KEY_BOX_OFFICE = "box_office";
    private static final String KEY_PRODUCTION = "production";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_DATE_SAVED = "date_saved";

    //endregion

    //region Constructor

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //endregion

    //region Overridden Methods

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_YEAR + " TEXT,"
                + KEY_RATED + " TEXT,"
                + KEY_RELEASED + " TEXT,"
                + KEY_RUNTIME + " TEXT,"
                + KEY_GENRE + " TEXT,"
                + KEY_DIRECTOR + " TEXT,"
                + KEY_WRITER + " TEXT,"
                + KEY_ACTORS + " TEXT,"
                + KEY_PLOT + " TEXT,"
                + KEY_LANGUAGE + " TEXT,"
                + KEY_COUNTRY + " TEXT,"
                + KEY_AWARDS + " TEXT,"
                + KEY_POSTER_URL + " TEXT,"
                + KEY_METASCORE + " TEXT,"
                + KEY_IMDB_RATING + " TEXT,"
                + KEY_IMDB_VOTES + " TEXT,"
                + KEY_IMDB_ID + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_DVD + " TEXT,"
                + KEY_BOX_OFFICE + " TEXT,"
                + KEY_PRODUCTION + " TEXt,"
                + KEY_WEBSITE + " TEXT,"
                + KEY_DATE_SAVED + " DATE"
                + ")";

        sqLiteDatabase.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(sqLiteDatabase);
    }

    //endregion

    //region CRUD Methods

    // Adding new movie.
    public void addMovie(Movie movie) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_YEAR, movie.getYear());
        values.put(KEY_RATED, movie.getRated());
        values.put(KEY_RELEASED, movie.getReleased());
        values.put(KEY_RUNTIME, movie.getRuntime());
        values.put(KEY_GENRE, movie.getGenre());
        values.put(KEY_DIRECTOR, movie.getDirector());
        values.put(KEY_WRITER, movie.getWriter());
        values.put(KEY_ACTORS, movie.getActors());
        values.put(KEY_PLOT, movie.getPlot());
        values.put(KEY_LANGUAGE, movie.getLanguage());
        values.put(KEY_COUNTRY, movie.getCountry());
        values.put(KEY_AWARDS, movie.getAwards());
        values.put(KEY_POSTER_URL, movie.getPosterUrl());
        values.put(KEY_METASCORE, movie.getMetascore());
        values.put(KEY_IMDB_RATING, movie.getImdbRating());
        values.put(KEY_IMDB_VOTES, movie.getImdbVotes());
        values.put(KEY_IMDB_ID, movie.getImdbId());
        values.put(KEY_TYPE, movie.getType());
        values.put(KEY_DVD, movie.getDvd());
        values.put(KEY_BOX_OFFICE, movie.getBoxOffice());
        values.put(KEY_PRODUCTION, movie.getProduction());
        values.put(KEY_WEBSITE, movie.getWebsite());
        values.put(KEY_DATE_SAVED, mDateFormat.format(movie.getDateSaved()));

        database.insert(TABLE_MOVIES, null, values);
        database.close();
    }

    // Getting single movie.
//    public Movie getMovie(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_MOVIES, new String[] { KEY_ID,
//                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        Movie contact = new Movie(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//
//        return contact;
//    }

    // Getting all movies.
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> contactList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor row = database.rawQuery(selectQuery, null);

        if (row.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Long.parseLong(row.getString(0)));
                movie.setTitle(row.getString(1));
                movie.setYear(row.getString(2));
                movie.setRated(row.getString(3));
                movie.setReleased(row.getString(4));
                movie.setRuntime(row.getString(5));
                movie.setGenre(row.getString(6));
                movie.setDirector(row.getString(7));
                movie.setWriter(row.getString(8));
                movie.setActors(row.getString(9));
                movie.setPlot(row.getString(10));
                movie.setLanguage(row.getString(11));
                movie.setCountry(row.getString(12));
                movie.setAwards(row.getString(13));
                movie.setPosterUrl(row.getString(14));
                movie.setMetascore(row.getString(15));
                movie.setImdbRating(row.getString(16));
                movie.setImdbVotes(row.getString(17));
                movie.setImdbId(row.getString(18));
                movie.setType(row.getString(19));
                movie.setDvd(row.getString(20));
                movie.setBoxOffice(row.getString(21));
                movie.setProduction(row.getString(22));
                movie.setWebsite(row.getString(23));

                String dateSavedString = row.getString(24);
                try {
                    movie.setDateSaved(mDateFormat.parse(dateSavedString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                contactList.add(movie);
            } while (row.moveToNext());
        }

        return contactList;
    }

    // Getting movies count.
    public int getMoviesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MOVIES;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor row = database.rawQuery(countQuery, null);
        row.close();

        return row.getCount();
    }

    // Updating single movie.
    public int updateMovie(Movie movie) {
        SQLiteDatabase database = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_YEAR, movie.getYear());
        values.put(KEY_RATED, movie.getRated());
        values.put(KEY_RELEASED, movie.getReleased());
        values.put(KEY_RUNTIME, movie.getRuntime());
        values.put(KEY_GENRE, movie.getGenre());
        values.put(KEY_DIRECTOR, movie.getDirector());
        values.put(KEY_WRITER, movie.getWriter());
        values.put(KEY_ACTORS, movie.getActors());
        values.put(KEY_PLOT, movie.getPlot());
        values.put(KEY_LANGUAGE, movie.getLanguage());
        values.put(KEY_COUNTRY, movie.getCountry());
        values.put(KEY_AWARDS, movie.getAwards());
        values.put(KEY_POSTER_URL, movie.getPosterUrl());
        values.put(KEY_METASCORE, movie.getMetascore());
        values.put(KEY_IMDB_RATING, movie.getImdbRating());
        values.put(KEY_IMDB_VOTES, movie.getImdbVotes());
        values.put(KEY_IMDB_ID, movie.getImdbId());
        values.put(KEY_TYPE, movie.getType());
        values.put(KEY_DVD, movie.getDvd());
        values.put(KEY_BOX_OFFICE, movie.getBoxOffice());
        values.put(KEY_PRODUCTION, movie.getProduction());
        values.put(KEY_WEBSITE, movie.getWebsite());
        values.put(KEY_DATE_SAVED, mDateFormat.format(calendar));

        // updating row
        return database.update(TABLE_MOVIES, values, KEY_ID + " = ?", new String[] { String.valueOf(movie.getId()) });
    }

    // Deleting single movie.
    public void deleteMovie(Movie movie) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_MOVIES, KEY_ID + " = ?", new String[] { String.valueOf(movie.getId()) });
        database.close();
    }

    //endregion
}
