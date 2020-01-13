package com.example.android.reddit_java.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;


import com.example.android.reddit_java.R;
import com.example.android.reddit_java.adapter.RVAdapter;
import com.example.android.reddit_java.custom_view.RVDivider;
import com.example.android.reddit_java.model.Child;
import com.example.android.reddit_java.model.Data_;
import com.example.android.reddit_java.model.RepoResult;
import com.example.android.reddit_java.util.Constant;
import com.example.android.reddit_java.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RVAdapter.Delegate{

    MyViewModel viewModel;
    RecyclerView recyclerView;
    RVAdapter adapter;
    Button button;
    SearchView searchView;
    MutableLiveData<List<Child>> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search_view);
        RecyclerView.ItemDecoration decoration = new RVDivider(this,getDrawable(R.drawable.divider));

        searchView.setQuery("funny",true);
        recyclerView.addItemDecoration(decoration);
        adapter = new RVAdapter(new ArrayList<Child>(),this.getApplicationContext(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        liveData = viewModel.getLiveData();

        viewModel.getLiveData().observe(this, new Observer<List<Child>>() {
            @Override
            public void onChanged(List<Child> children) {
                adapter.setChildList(children);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.test("funny");

//        viewModel.resultCallback("funny").enqueue(new Callback<RepoResult>() {
//            @Override
//            public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
//                if(response.body().getData()!=null) {
//                    Log.d("TAG_X", "onResponse: " + response.body().getData().getChildren().toString());
//                    adapter.setChildList(response.body().getData().getChildren());
//                    adapter.notifyDataSetChanged();
//                }else{
//                    Log.d("TAG_X", "onResponse: is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RepoResult> call, Throwable t) {
//                Log.d("TAG_X", "onFailure: "+t.getMessage());
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchView.getQuery().toString();
                if(!text.equals(""))
                    viewModel.test(text);
                try {
                    ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(button.getWindowToken(), 0);
                }catch(NullPointerException n){
                    Log.d("TAG_X", "onClick: "+n.getMessage());
                }
            }
        });

    }

//    private void changeData(String toSearch){
//        viewModel.resultCallback(toSearch).enqueue(new Callback<RepoResult>() {
//            @Override
//            public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
//                if(response.body().getData()!=null){
//                    Log.d("TAG_X", "onResponse: "+response.body().getData().getChildren().toString());
//                    adapter.setChildList(response.body().getData().getChildren());
//                    adapter.notifyDataSetChanged();
//                }else{
//                    Log.d("TAG_X", "onResponse: is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RepoResult> call, Throwable t) {
//                Log.d("TAG_X", "onFailure: "+t.getMessage());
//            }
//        });
//    }

    @Override
    public void onClick(Data_ data_) {
        SubredditFragment fragment = new SubredditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DATA_KEY,data_);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container,fragment)
                .addToBackStack(fragment.getTag())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        liveData.removeObservers(this);
    }
}
