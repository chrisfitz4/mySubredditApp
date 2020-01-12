package com.example.android.reddit_java.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.android.reddit_java.model.RepoResult;
import com.example.android.reddit_java.network.RedditRetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyViewModel extends AndroidViewModel {

    private RedditRetrofitInstance instance;

    public MyViewModel(@NonNull Application application) {
        super(application);
        instance = new RedditRetrofitInstance();
    }

    public void test(){
        Log.d("TAG_X", "test: ");
        resultCallback("funny").enqueue(new Callback<RepoResult>() {
            @Override
            public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
                Log.d("TAG_X", "onResponse: "+response.body().getData().getChildren().toString());
            }

            @Override
            public void onFailure(Call<RepoResult> call, Throwable t) {
                Log.d("TAG_X", "onFailure: "+t.getMessage());
            }
        });
    }

    public Call<RepoResult> resultCallback(String toSearch){
        Log.d("TAG_X", "resultCallback: ");
        return instance.makeCall(toSearch);
    }

}
