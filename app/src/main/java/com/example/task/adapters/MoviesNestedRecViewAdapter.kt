package com.example.task.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.activities.ViewAllMoviesActivity
import com.example.task.model.MovieBrief
import com.example.task.utils.AppConstants
import com.example.task.viewmodel.NestedRecViewModel


class MoviesNestedRecViewAdapter(mNestedList: List<NestedRecViewModel>, mContext: Context) :
    RecyclerView.Adapter<MoviesNestedRecViewAdapter.ViewHolder>() {
    private val mNestedList: List<NestedRecViewModel>
    private val mContext: Context

    init {
        this.mNestedList = mNestedList
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.nested_rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (mNestedList[position].getmGenreId()) {
            28 -> {
                holder.nested_heading.text = "Watch it Again"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.ACTION_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            12 -> {
                holder.nested_heading.text = "Action & Adventure"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.ADVENTURE_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            16 -> {
                holder.nested_heading.text = "Top Searches"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.ANIMATION_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            35 -> {
                holder.nested_heading.text = "Animation"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.COMEDY_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            80 -> {
                holder.nested_heading.text = "Only on Netflix"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.CRIME_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            99 -> {
                holder.nested_heading.text = "Documentary"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(
                        AppConstants.VIEW_ALL_MOVIES_TYPE,
                        AppConstants.DOCUMENTARY_MOVIES_TYPE
                    )
                    mContext.startActivity(intent)
                }
            }
            18 -> {
                holder.nested_heading.text = "Drama"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.DRAMA_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            10751 -> {
                holder.nested_heading.text = "Family"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.FAMILY_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            14 -> {
                holder.nested_heading.text = "Fantasy"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.FANTASY_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            36 -> {
                holder.nested_heading.text = "History"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.HISTORY_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            27 -> {
                holder.nested_heading.text = "Horror"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.HORROR_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            10402 -> {
                holder.nested_heading.text = "Music"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.MUSIC_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            9648 -> {
                holder.nested_heading.text = "Mystery"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.MYSTERY_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            878 -> {
                holder.nested_heading.text = "Science Fiction"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.SCIFI_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }

            53 -> {
                holder.nested_heading.text = "Thriller"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.THRILLER_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }
            10752 -> {
                holder.nested_heading.text = "War"
                holder.nested_view_all.setOnClickListener { view: View? ->
                    val intent = Intent(
                        mContext,
                        ViewAllMoviesActivity::class.java
                    )
                    intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.WAR_MOVIES_TYPE)
                    mContext.startActivity(intent)
                }
            }

        }
        setMovieRecView(holder.nested_recView, mNestedList[position].getmMovies())
    }

    override fun getItemCount(): Int {
        return mNestedList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nested_heading_layout: ConstraintLayout
        var nested_heading: TextView
        var nested_view_all: TextView
        var nested_recView: RecyclerView

        init {
            nested_heading_layout = itemView.findViewById(R.id.nested_constraint_layout)
            nested_heading = itemView.findViewById(R.id.nested_heading)
            nested_view_all = itemView.findViewById(R.id.nested_view_all)
            nested_recView = itemView.findViewById(R.id.nested_recView)
        }
    }

    private fun setMovieRecView(recyclerView: RecyclerView, mMovies: List<MovieBrief>) {
        val mMoviesAdapter = MovieviewallAdapter(mMovies, mContext)
        recyclerView.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = mMoviesAdapter
    }
}