package com.example.task.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.adapters.MovieviewallAdapter
import com.example.task.model.GenreMoviesResponse
import com.example.task.model.MovieBrief
import com.example.task.model.PopularMoviesResponse
import com.example.task.model.TopRatedMoviesResponse
import com.example.task.network.ApiClient
import com.example.task.network.ApiInterface
import com.example.task.utils.AppConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewAllMoviesActivity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mMovies: MutableList<MovieBrief>? = null
    private var mMoviesAdapter: MovieviewallAdapter? = null
    private var mMovieType = 0
    private var pagesOver = false
    private var presentPage = 1
    private var loading = true
    private var previousTotal = 0
    private val visibleThreshold = 5
    lateinit var mPopularMoviesCall: Call<PopularMoviesResponse>
    lateinit var mTopRatedMoviesCall: Call<TopRatedMoviesResponse>
    lateinit var mGenreMoviesResponseCall: Call<GenreMoviesResponse>
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_movies)
        val receivedIntent = intent
        mMovieType = receivedIntent.getIntExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, -1)
        if (mMovieType == -1) finish()
        when (mMovieType) {
            AppConstants.POPULAR_MOVIES_TYPE -> title = "Popular on Netflix"
            AppConstants.TOP_RATED_MOVIES_TYPE -> title = "Trending Now"
            AppConstants.ACTION_MOVIES_TYPE -> title = "Watch it Again"
            AppConstants.ADVENTURE_MOVIES_TYPE -> title = "Adventure Movies"
            AppConstants.ANIMATION_MOVIES_TYPE -> title = "Animation Movies"
            AppConstants.COMEDY_MOVIES_TYPE -> title = "Comedy Movies"
            AppConstants.CRIME_MOVIES_TYPE -> title = "Crime Movies"
            AppConstants.DOCUMENTARY_MOVIES_TYPE -> title = "Documentary Movies"
            AppConstants.DRAMA_MOVIES_TYPE -> title = "Drama Movies"
            AppConstants.FAMILY_MOVIES_TYPE -> title = "Family Movies"
            AppConstants.FANTASY_MOVIES_TYPE -> title = "Fantasy Movies"
            AppConstants.HISTORY_MOVIES_TYPE -> title = "History Movies"
            AppConstants.HORROR_MOVIES_TYPE -> title = "Horror Movies"
            AppConstants.MUSIC_MOVIES_TYPE -> title = "Music Movies"
            AppConstants.MYSTERY_MOVIES_TYPE -> title = "Mystery Movies"
            AppConstants.SCIFI_MOVIES_TYPE -> title = "Sci-Fi Movies"
            AppConstants.THRILLER_MOVIES_TYPE -> title = "Thriller Movies"
            AppConstants.WAR_MOVIES_TYPE -> title = "War Movies"
        }
       // toolbar.setTitle(title)
        mRecyclerView = findViewById<View>(R.id.view_movies_recView) as RecyclerView
        mMovies = ArrayList<MovieBrief>()
        mMoviesAdapter = MovieviewallAdapter(mMovies as ArrayList<MovieBrief>, this@ViewAllMoviesActivity)
        mRecyclerView!!.adapter = mMoviesAdapter
        val gridLayoutManager = GridLayoutManager(this@ViewAllMoviesActivity, 3)
        mRecyclerView!!.layoutManager = gridLayoutManager
        mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                    loadMovies(mMovieType)
                    loading = true
                }
            }
        })
        loadMovies(mMovieType)
        findViewById<ImageView>(R.id.img_back).setOnClickListener { onBackPressed() }
    }

    private fun loadMovies(movieType: Int) {
        if (pagesOver) return
        val apiService: ApiInterface = ApiClient.getMovieApi()
        when (movieType) {
            AppConstants.POPULAR_MOVIES_TYPE -> {
                mPopularMoviesCall = apiService.getPopularMovies(AppConstants.API_KEY, presentPage)
                mPopularMoviesCall!!.enqueue(object : Callback<PopularMoviesResponse> {
                    override fun onResponse(
                        call: Call<PopularMoviesResponse>,
                        response: Response<PopularMoviesResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mPopularMoviesCall = call.clone()
                            mPopularMoviesCall!!.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.results == null) return
                        for (movieBrief in response.body()!!.results) {
                            if (movieBrief != null && movieBrief.getTitle() != null && movieBrief.getPosterPath() != null) mMovies!!.add(
                                movieBrief
                            )
                        }
                        mMoviesAdapter!!.notifyDataSetChanged()
                        if (response.body()!!.getPage() === response.body()!!
                                .getTotalPages()
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<PopularMoviesResponse?>, t: Throwable) {}
                })
            }
            AppConstants.TOP_RATED_MOVIES_TYPE -> {
                mTopRatedMoviesCall =
                    apiService.getTopRatedMovies(AppConstants.API_KEY, presentPage, "US")
                mTopRatedMoviesCall!!.enqueue(object : Callback<TopRatedMoviesResponse> {
                    override fun onResponse(
                        call: Call<TopRatedMoviesResponse>,
                        response: Response<TopRatedMoviesResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mTopRatedMoviesCall = call.clone()
                            mTopRatedMoviesCall!!.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.getResults() == null) return
                        for (movieBrief in response.body()!!.getResults()) {
                            if (movieBrief != null && movieBrief.getTitle() != null && movieBrief.getPosterPath() != null) mMovies!!.add(
                                movieBrief
                            )
                        }
                        mMoviesAdapter!!.notifyDataSetChanged()
                        if (response.body()!!.getPage() === response.body()!!
                                .getTotalPages()
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<TopRatedMoviesResponse?>, t: Throwable) {}
                })
            }
            else -> {
                mGenreMoviesResponseCall = apiService.getMoviesByGenre(AppConstants.API_KEY, movieType, presentPage)
                mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
                    override fun onResponse(
                        call: Call<GenreMoviesResponse>,
                        response: Response<GenreMoviesResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mGenreMoviesResponseCall = call.clone()
                            mGenreMoviesResponseCall!!.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.results == null) return
                        for (movieBrief in response.body()!!.results) {
                            if (movieBrief?.posterPath != null) {
                                mMovies!!.add(movieBrief)
                            }
                        }
                        mMoviesAdapter!!.notifyDataSetChanged()
                        if (response.body()!!.getPage()
                                .equals(response.body()!!.getTotalPages())
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<GenreMoviesResponse?>, t: Throwable) {}
                })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}