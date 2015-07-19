package com.gorrotowi.popularmoviesone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {

    JSONObject jsonMovie;
    TextView txtTitle, txtDate, txtRate, txtDesc;
    ImageView imgMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imgMovie = (ImageView) findViewById(R.id.img_movie_poster);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDate = (TextView) findViewById(R.id.txtReleaseDate);
        txtRate = (TextView) findViewById(R.id.txtRate);
        txtDesc = (TextView) findViewById(R.id.txtDescr);
        try {
            jsonMovie = new JSONObject(getIntent().getExtras().getString("jsondata"));
            Picasso.with(this).load(getString(R.string.base_img_url)+jsonMovie.getString("poster_path")).into(imgMovie);
            txtTitle.setText(jsonMovie.getString("title"));
            txtDate.setText(jsonMovie.getString("release_date"));
            txtRate.setText(jsonMovie.getString("vote_average")+"/10");
            txtDesc.setText(jsonMovie.getString("overview"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
