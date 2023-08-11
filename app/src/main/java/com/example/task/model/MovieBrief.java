package com.example.task.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieBrief {

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;


    public MovieBrief( Integer id, String title, String posterPath) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;

    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }






}
