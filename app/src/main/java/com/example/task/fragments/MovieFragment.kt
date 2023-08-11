package com.example.task.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.activities.ViewAllMoviesActivity
import com.example.task.adapters.MovieviewallAdapter
import com.example.task.adapters.MoviesNestedRecViewAdapter
import com.example.task.model.*
import com.example.task.network.ApiClient
import com.example.task.network.ApiInterface
import com.example.task.utils.AppConstants
import com.example.task.viewmodel.NestedRecViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MovieFragment : Fragment() {
    lateinit var  mProgressBar: ProgressBar
    lateinit var  mViewPopular: TextView
    lateinit var  mViewTopRated: TextView
    private  var  timer: Timer? = null
    private  var  timerTask: TimerTask? = null
     var  position = 0
    lateinit var  carouselLayoutManager: LinearLayoutManager
    lateinit var  mNowShowingMovies: MutableList<MovieBrief>
    lateinit var  mPopularMoviesRecyclerView: RecyclerView
    lateinit var  mPopularMovies: MutableList<MovieBrief>
    lateinit var  mPopularMoviesAdapter: MovieviewallAdapter
    lateinit var  mTopRatedRecyclerView: RecyclerView
    lateinit var  mTopRatedMovies: MutableList<MovieBrief>
    lateinit var  mTopRatedAdapter: MovieviewallAdapter
    lateinit var  mActionMovies: MutableList<MovieBrief>
    lateinit var  mAdventureMovies: MutableList<MovieBrief>
    lateinit var  mAnimatedMovies: MutableList<MovieBrief>
    lateinit var  mComedyMovies: MutableList<MovieBrief>
    lateinit var  mCrimeMovies: MutableList<MovieBrief>
    lateinit var  mDocumentaryMovies: MutableList<MovieBrief>
    lateinit var  mDramaMovies: MutableList<MovieBrief>
    lateinit var  mFamilyMovies: MutableList<MovieBrief>
    lateinit var  mFantasyMovies: MutableList<MovieBrief>
    lateinit var  mHistoryMovies: MutableList<MovieBrief>
    lateinit var  mHorrorMovies: MutableList<MovieBrief>
    lateinit var  mMusicMovies: MutableList<MovieBrief>
    lateinit var  mMysteryMovies: MutableList<MovieBrief>
    lateinit var  mSciFiMovies: MutableList<MovieBrief>
    lateinit var  mThrillerMovies: MutableList<MovieBrief>
    lateinit var  mWarMovies: MutableList<MovieBrief>
    lateinit var  mNestedRecView: RecyclerView
    lateinit var  mNestedList: MutableList<NestedRecViewModel>
    lateinit var  mMoviesNestedRecViewAdapter: MoviesNestedRecViewAdapter
    lateinit var  mPopularHeading: ConstraintLayout
    lateinit var  mTopRatedHeading: ConstraintLayout
    var  mNowShowingMoviesLoaded = false
     var  mPopularMoviesLoaded = false
     var  mTopRatedMoviesLoaded = false

    lateinit var mPopularMoviesCall: Call<PopularMoviesResponse>
    lateinit var mTopRatedMoviesCall: Call<TopRatedMoviesResponse>
    lateinit var mGenreMoviesResponseCall: Call<GenreMoviesResponse>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProgressBar = view.findViewById(R.id.movie_progressBar)
        mViewPopular = view.findViewById(R.id.view_popular)
        mViewTopRated = view.findViewById(R.id.view_top_rated)
        mPopularMoviesRecyclerView = view.findViewById(R.id.popular_recView)
        mTopRatedRecyclerView = view.findViewById(R.id.top_rated_recView)
        mPopularHeading = view.findViewById(R.id.popular_heading)
        mTopRatedHeading = view.findViewById(R.id.top_rated_heading)
        mNestedRecView = view.findViewById(R.id.movie_nested_recView)
        mNowShowingMovies = ArrayList<MovieBrief>()
        mPopularMovies = ArrayList<MovieBrief>()
        mTopRatedMovies = ArrayList<MovieBrief>()
        mActionMovies = ArrayList<MovieBrief>()
        mAnimatedMovies = ArrayList<MovieBrief>()
        mAdventureMovies = ArrayList<MovieBrief>()
        mComedyMovies = ArrayList<MovieBrief>()
        mCrimeMovies = ArrayList<MovieBrief>()
        mDocumentaryMovies = ArrayList<MovieBrief>()
        mDramaMovies = ArrayList<MovieBrief>()
        mFamilyMovies = ArrayList<MovieBrief>()
        mFantasyMovies = ArrayList<MovieBrief>()
        mHistoryMovies = ArrayList<MovieBrief>()
        mHorrorMovies = ArrayList<MovieBrief>()
        mMusicMovies = ArrayList<MovieBrief>()
        mMysteryMovies = ArrayList<MovieBrief>()
        mSciFiMovies = ArrayList<MovieBrief>()
        mThrillerMovies = ArrayList<MovieBrief>()
        mWarMovies = ArrayList<MovieBrief>()
        mNestedList = ArrayList<NestedRecViewModel>()
        mNowShowingMoviesLoaded = false
        mPopularMoviesLoaded = false
        mTopRatedMoviesLoaded = false
        mPopularMoviesAdapter = MovieviewallAdapter(mPopularMovies, requireContext())
        mPopularMoviesRecyclerView.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        mPopularMoviesRecyclerView.setAdapter(mPopularMoviesAdapter)
        mTopRatedAdapter = MovieviewallAdapter(mTopRatedMovies, requireContext())
        mTopRatedRecyclerView.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        mTopRatedRecyclerView.setAdapter(mTopRatedAdapter)
        mMoviesNestedRecViewAdapter = MoviesNestedRecViewAdapter(mNestedList, requireContext())
        mNestedRecView.setLayoutManager(LinearLayoutManager(context))
        mNestedRecView.setAdapter(mMoviesNestedRecViewAdapter)

        mViewPopular.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ViewAllMoviesActivity::class.java)
            intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.POPULAR_MOVIES_TYPE)
            startActivity(intent)
        })
        mViewTopRated.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ViewAllMoviesActivity::class.java)
            intent.putExtra(AppConstants.VIEW_ALL_MOVIES_TYPE, AppConstants.TOP_RATED_MOVIES_TYPE)
            startActivity(intent)
        })
        initViews()
    }

    private fun stopAutoScrollCarousel() {
        if (timer != null && timerTask != null) {
            timerTask!!.cancel()
            timer!!.cancel()
            timer = null!!
            timerTask = null!!
            position = carouselLayoutManager.findFirstCompletelyVisibleItemPosition()
        }
    }

    private fun initViews() {
        loadPopularMovies()
        loadTopRatedMovies()
        loadActionMovies()
        loadAdventureMovies()
        loadAnimatedMovies()
        loadComedyMovies()
        loadCrimeMovies()
        loadDocumentaryMovies()
        loadDramaMovies()
        loadFamilyMovies()
        loadFantasyMovies()
        loadHistoryMovies()
        loadHorrorMovies()
        loadMusicMovies()
        loadMysteryMovies()
        loadSciFiMovies()
        loadThriller()
        loadWarMovies()
    }



    private fun loadPopularMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mPopularMoviesCall = apiInterface.getPopularMovies(AppConstants.API_KEY, 1)
        (mPopularMoviesCall as Call<PopularMoviesResponse>?)!!.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                if (!response.isSuccessful) {
                    mPopularMoviesCall = call.clone()
                    mPopularMoviesCall!!.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief?.posterPath != null) mPopularMovies!!.add(
                        movieBrief
                    )
                }
                mPopularMoviesAdapter!!.notifyDataSetChanged()
                mPopularMoviesLoaded = true
                checkAllDataLoaded()
            }

            override fun onFailure(call: Call<PopularMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadTopRatedMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mTopRatedMoviesCall = apiInterface.getTopRatedMovies(AppConstants.API_KEY, 1, "US")
        (mTopRatedMoviesCall as Call<TopRatedMoviesResponse>?)!!.enqueue(object : Callback<TopRatedMoviesResponse> {
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
                for (movieBrief in response.body()!!.results) {
                    if (movieBrief?.posterPath != null) mTopRatedMovies!!.add(
                        movieBrief
                    )
                }
                mTopRatedAdapter!!.notifyDataSetChanged()
                mTopRatedMoviesLoaded = true
                checkAllDataLoaded()
            }

            override fun onFailure(call: Call<TopRatedMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadActionMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.ACTION_MOVIES_TYPE, 1)
        (mGenreMoviesResponseCall as Call<GenreMoviesResponse>?)!!.enqueue(object : Callback<GenreMoviesResponse> {
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
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief?.posterPath != null) {
                        mActionMovies!!.add(movieBrief)
                    }
                }
                mActionMovies?.let { NestedRecViewModel(it, AppConstants.ACTION_MOVIES_TYPE) }
                    ?.let { mNestedList!!.add(it) }
                mNestedRecView!!.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadAdventureMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.ADVENTURE_MOVIES_TYPE, 1)
        (mGenreMoviesResponseCall as Call<GenreMoviesResponse>?)!!.enqueue(object : Callback<GenreMoviesResponse> {
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
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mAdventureMovies!!.add(movieBrief)
                    }
                }
                mAdventureMovies?.let {
                    NestedRecViewModel(
                        it,
                        AppConstants.ADVENTURE_MOVIES_TYPE
                    )
                }?.let {
                    mNestedList!!.add(
                        it
                    )
                }
                mNestedRecView!!.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadAnimatedMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.ANIMATION_MOVIES_TYPE, 1)
        (mGenreMoviesResponseCall as Call<GenreMoviesResponse>?)!!.enqueue(object : Callback<GenreMoviesResponse> {
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
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief?.posterPath != null) {
                        mAnimatedMovies!!.add(movieBrief)
                    }
                }
                mAnimatedMovies?.let {
                    NestedRecViewModel(
                        it,
                        AppConstants.ANIMATION_MOVIES_TYPE
                    )
                }?.let {
                    mNestedList!!.add(
                        it
                    )
                }
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadComedyMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.COMEDY_MOVIES_TYPE, 1)
        (mGenreMoviesResponseCall as Call<GenreMoviesResponse>?)!!.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mComedyMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mComedyMovies!!, AppConstants.COMEDY_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadCrimeMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.CRIME_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mCrimeMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mCrimeMovies!!, AppConstants.CRIME_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadDocumentaryMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.DOCUMENTARY_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mDocumentaryMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(
                    NestedRecViewModel(
                        mDocumentaryMovies!!,
                        AppConstants.DOCUMENTARY_MOVIES_TYPE
                    )
                )
                mMoviesNestedRecViewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadDramaMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.DRAMA_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mDramaMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mDramaMovies!!, AppConstants.DRAMA_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadFamilyMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.FAMILY_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mFamilyMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mFamilyMovies!!, AppConstants.FAMILY_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadFantasyMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.FANTASY_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mFantasyMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mFantasyMovies!!, AppConstants.FANTASY_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadHistoryMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.HISTORY_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mHistoryMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mHistoryMovies!!, AppConstants.HISTORY_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadHorrorMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.HORROR_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mHorrorMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mHorrorMovies!!, AppConstants.HORROR_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadMusicMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.MUSIC_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mMusicMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mMusicMovies!!, AppConstants.MUSIC_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadMysteryMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.MYSTERY_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mMysteryMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mMysteryMovies!!, AppConstants.MYSTERY_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadSciFiMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.SCIFI_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mSciFiMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mSciFiMovies!!, AppConstants.SCIFI_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }
    private fun loadThriller() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.THRILLER_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mThrillerMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(
                    NestedRecViewModel(
                        mThrillerMovies!!,
                        AppConstants.THRILLER_MOVIES_TYPE
                    )
                )
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }

    private fun loadWarMovies() {
        val apiInterface: ApiInterface = ApiClient.getMovieApi()
        mGenreMoviesResponseCall =
            apiInterface.getMoviesByGenre(AppConstants.API_KEY, AppConstants.WAR_MOVIES_TYPE, 1)
        mGenreMoviesResponseCall.enqueue(object : Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (!response.isSuccessful()) {
                    mGenreMoviesResponseCall = call.clone()
                    mGenreMoviesResponseCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.getResults() == null) return
                for (movieBrief in response.body()!!.getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null) {
                        mWarMovies!!.add(movieBrief)
                    }
                }
                mNestedList!!.add(NestedRecViewModel(mWarMovies!!, AppConstants.WAR_MOVIES_TYPE))
            }

            override fun onFailure(call: Call<GenreMoviesResponse?>?, t: Throwable?) {}
        })
    }


    private fun checkAllDataLoaded() {
        if (mPopularMoviesLoaded && mTopRatedMoviesLoaded) {
            mProgressBar!!.visibility = View.GONE
            mPopularHeading!!.visibility = View.VISIBLE
            mPopularMoviesRecyclerView!!.visibility = View.VISIBLE
            mTopRatedHeading!!.visibility = View.VISIBLE
            mTopRatedRecyclerView!!.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        stopAutoScrollCarousel()
    }

    override fun onResume() {
        super.onResume()
    }
}