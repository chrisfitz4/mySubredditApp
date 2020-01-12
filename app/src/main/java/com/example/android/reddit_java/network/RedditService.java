package com.example.android.reddit_java.network;

import com.example.android.reddit_java.model.RepoResult;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RedditService {

    @GET("/r/{description}/.json")
    Call<RepoResult> callReddit(
            @Path("description") String toSearch
    );

}
