package com.gorrotowi.popularmoviesone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gorrotowi.popularmoviesone.adapters.AdapterMovies;
import com.gorrotowi.popularmoviesone.entitys.ItemImgMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcMovies;
    AdapterMovies adapterMovies;
    RequestQueue rq;
    JsonObjectRequest jsonObjectRequest;
    int lastreq = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rq = Volley.newRequestQueue(this);

        rcMovies = (RecyclerView) findViewById(R.id.recyclerMovies);
        rcMovies.setLayoutManager(new GridLayoutManager(this, 2));

        getMovies(getString(R.string.query_popularity));

    }

    public void getMovies(final String queryMovie) {

        String url = getString(R.string.base_url_api) + queryMovie + "&" + getString(R.string.api_key);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<ItemImgMovie> item = new ArrayList<>();
                    JSONArray arrayMovies = response.getJSONArray("results");
                    for (int i = 0; i < arrayMovies.length(); i++) {
                        item.add(new ItemImgMovie(arrayMovies.getJSONObject(i).getString("poster_path"), arrayMovies.getJSONObject(i)));
                    }
                    adapterMovies = new AdapterMovies(item, MainActivity.this);
                    rcMovies.setAdapter(adapterMovies);

                    if (queryMovie.equals(getString(R.string.query_popularity))) {
                        lastreq = 0;
                    } else {
                        lastreq = 1;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(jsonObjectRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_most_popular:
                if (lastreq == 1) {
                    getMovies(getString(R.string.query_popularity));
                }
                return true;
            case R.id.action_most_voted:
                if (lastreq == 0) {
                    getMovies(getString(R.string.query_mostVoted));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
