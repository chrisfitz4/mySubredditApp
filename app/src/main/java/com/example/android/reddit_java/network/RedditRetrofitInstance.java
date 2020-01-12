package com.example.android.reddit_java.network;

import com.example.android.reddit_java.model.RepoResult;
import com.example.android.reddit_java.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RedditRetrofitInstance {

    private RedditService redditService;

    public RedditRetrofitInstance(){
        redditService = createService(createRetrofit());
    }

    private Retrofit createRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private RedditService createService(Retrofit retrofit){
        return retrofit.create(RedditService.class);
    }

    public Call<RepoResult> makeCall(String toSearch){
        return redditService.callReddit(toSearch);
    }

}
