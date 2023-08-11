package com.example.task.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    private String homepage;
    @SerializedName("id")
    private Integer id;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("overview")
    private String overview;
    @SerializedName("title")
    private String title;


    public Movie(Integer id, String imdbId, String overview,  String title) {
        this.id = id;
        this.imdbId = imdbId;
        this.overview = overview;
        this.title = title;

    }










    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
