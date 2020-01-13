package com.example.android.reddit_java.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.android.reddit_java.model.Child;
import com.example.android.reddit_java.model.RepoResult;
import com.example.android.reddit_java.network.RedditRetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyViewModel extends AndroidViewModel {

    private RedditRetrofitInstance instance;
    private MutableLiveData<List<Child>> liveData;

    public MyViewModel(@NonNull Application application) {
        super(application);
        instance = new RedditRetrofitInstance();
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Child>> getLiveData(){
        return liveData;
    }

    public void test(String toSearch){
        Log.d("TAG_X", "test: ");
        resultCallback(toSearch).enqueue(new Callback<RepoResult>() {
            @Override
            public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
                try {
                    liveData.setValue(response.body().getData().getChildren());
                    Log.d("TAG_X", "onResponse: " + response.body().getData().getChildren().toString());
                }catch (NullPointerException n){
                    Toast.makeText(getApplication(),"Sorry! Nothing came up",Toast.LENGTH_SHORT).show();
                    Log.d("TAG_X", "onResponse: "+n.getMessage());
                }
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
