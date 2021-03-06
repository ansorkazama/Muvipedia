package com.ansorkazama.themuvipedia;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ansorkazama.themuvipedia.data.MuviContract;

public class MovieDetailActivity extends AppCompatActivity {

    private TrafficManager mTM = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        mTM = TrafficManager.getInstance(this);
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            Uri uri = (Uri) getIntent().getData();

            String mode = MuviContract.MoviePopular.getMovieModeFromUri(uri);
            if (mode.contains("popular")) {
                setTitle(R.string.drawer_popular_movies);
                mTM.setDetailDisplayMode(getResources().getString(R.string.drawer_popular_movies));
            } else if (mode.contains("top_rated")) {
                setTitle(R.string.drawer_top_rated);
                mTM.setDetailDisplayMode(getResources().getString(R.string.drawer_top_rated));
            } else if (mode.contains("favorites")) {
                setTitle(R.string.drawer_favorites);
                mTM.setDetailDisplayMode(getResources().getString(R.string.drawer_favorites));
            }
            arguments.putParcelable(MovieDetailFragment.DETAIL_URI, getIntent().getData());

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        String display_mode = getTitle().toString();
        mTM.setDetailDisplayMode(display_mode);
    }

    @Override
    public void onResume() {
        super.onResume();
        String display_mode = mTM.getDetailDisplayMode();
        if (display_mode != null) {
                setTitle(display_mode);
        }
    }
}
