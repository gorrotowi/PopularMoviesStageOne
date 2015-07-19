package com.gorrotowi.popularmoviesone.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gorrotowi.popularmoviesone.MovieDetailActivity;
import com.gorrotowi.popularmoviesone.R;
import com.gorrotowi.popularmoviesone.entitys.ItemImgMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gorro on 19/07/15.
 */
public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.MoviewViewHolder> {

    List<ItemImgMovie> itemImgMovies;
    Context context;

    public AdapterMovies(List<ItemImgMovie> itemImgMovies, Context context) {
        this.itemImgMovies = itemImgMovies;
        this.context = context;
    }

    @Override
    public MoviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        MoviewViewHolder holder = new MoviewViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MoviewViewHolder holder, final int position) {
        Picasso.with(context)
                .load(context.getString(R.string.base_img_url) + itemImgMovies.get(position).getImgpath())
//                .placeholder(R.drawable.abc_btn_check_to_on_mtrl_015)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MovieDetailActivity.class).putExtra("jsondata", itemImgMovies.get(position).getJsonMovie().toString()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemImgMovies.size();
    }

    public static class MoviewViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MoviewViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_movie);
        }
    }

}
