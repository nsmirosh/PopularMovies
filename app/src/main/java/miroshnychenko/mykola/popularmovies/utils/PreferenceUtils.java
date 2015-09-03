package miroshnychenko.mykola.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by nsmirosh on 8/27/2015.
 */
public class PreferenceUtils {

    public static final String DEFAULT_SORT_CRITERIA = "popularity.desc";
    public static final String PREFS_KEY_SORT_CRITERIA = "sortCriteria";
    public static final String FAVORITES_KEY = "favoritesKey";

    private SharedPreferences mPrefs;

    public PreferenceUtils(Context context) {
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveSortCriteria(String sortCriteria) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREFS_KEY_SORT_CRITERIA, sortCriteria);
        editor.apply();
    }

    public void saveFavoriteMovie(String movieId) {
        SharedPreferences.Editor editor = mPrefs.edit();
        Set<String> favorites = mPrefs.getStringSet(FAVORITES_KEY, new HashSet<String>());
        favorites.add(movieId);
        editor.putStringSet(FAVORITES_KEY, favorites);
        editor.apply();
    }

    public void deleteFavoriteMovie(String movieId) {
        SharedPreferences.Editor editor = mPrefs.edit();
        Set<String> favorites = mPrefs.getStringSet(FAVORITES_KEY, new HashSet<String>());
        favorites.remove(movieId);
        editor.putStringSet(FAVORITES_KEY, favorites);
        editor.apply();
    }




    public String getSortCriteria() {
        return mPrefs.getString(PREFS_KEY_SORT_CRITERIA, DEFAULT_SORT_CRITERIA);
    }
}
