package com.example.task.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.task.R
import com.example.task.activities.MovieDetailsActivity
import com.example.task.model.MovieBrief
import com.example.task.utils.AppConstants

class MovieviewallAdapter(mMovies: List<MovieBrief>, mContext: Context) :
    RecyclerView.Adapter<MovieviewallAdapter.MovieViewHolder>() {
    private val mMovies: List<MovieBrief>
    private val mContext: Context

    init {
        this.mMovies = mMovies
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.small_single_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(mContext.applicationContext)
            .load(AppConstants.IMAGE_LOADING_BASE_URL_342 + mMovies[position].getPosterPath())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.movie_imageView)
        if (mMovies[position].getTitle() != null) holder.movie_title.setText(mMovies[position].getTitle()) else holder.movie_title.text = ""
    }



    override fun getItemCount(): Int {
        return mMovies.size
    }



    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movie_imageView: ImageView
        var movie_title: TextView
        var movie_cardView: CardView

        init {
            movie_imageView = itemView.findViewById(R.id.image_view_show_card)
            movie_title = itemView.findViewById(R.id.text_view_title_show_card)
            movie_cardView = itemView.findViewById(R.id.card_view_show_card)
            movie_imageView.layoutParams.width =
                (mContext.resources.displayMetrics.widthPixels * 0.31).toInt()
            movie_imageView.layoutParams.height =
                (mContext.resources.displayMetrics.widthPixels * 0.31 / 0.66).toInt()
            movie_cardView.setOnClickListener {
                val intent = Intent(mContext, MovieDetailsActivity::class.java)
                intent.putExtra("movie_id", mMovies[adapterPosition].getId())
                mContext.startActivity(intent)
            }
        }
    }



}