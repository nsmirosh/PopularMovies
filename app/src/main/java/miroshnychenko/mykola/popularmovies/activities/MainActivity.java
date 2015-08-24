package miroshnychenko.mykola.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.fragments.DetailFragment;
import miroshnychenko.mykola.popularmovies.fragments.MoviesFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailFragment(), DetailFragment.FRAGMENT_TAG)
                        .commit();
            }
        }
    }
}
