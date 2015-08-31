package miroshnychenko.mykola.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nsmirosh on 8/27/2015.
 */
public class PreferenceUtils {

    public static final String DEFAULT_SORT_CRITERIA = "popularity.desc";
    public static final String PREFS_KEY_SORT_CRITERIA = "sortCriteria";

    private SharedPreferences mPrefs;

    public PreferenceUtils(Context context) {
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveSortCriteria(String sortCriteria) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREFS_KEY_SORT_CRITERIA, sortCriteria);
        editor.apply();
    }

    public String getSortCriteria() {
        return mPrefs.getString(PREFS_KEY_SORT_CRITERIA, DEFAULT_SORT_CRITERIA);
    }
}
