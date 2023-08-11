package com.example.task.viewmodel

import com.example.task.model.MovieBrief


class NestedRecViewModel(mMovies: List<MovieBrief>, mGenreId: Int) {
    private var mMovies: List<MovieBrief>
    private var mGenreId: Int

    init {
        this.mMovies = mMovies
        this.mGenreId = mGenreId
    }

    fun getmMovies(): List<MovieBrief> {
        return mMovies
    }

    fun getmGenreId(): Int {
        return mGenreId
    }

}