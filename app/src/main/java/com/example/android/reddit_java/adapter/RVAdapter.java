package com.example.android.reddit_java.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.reddit_java.R;
import com.example.android.reddit_java.model.Child;
import com.example.android.reddit_java.model.Data_;
import com.example.android.reddit_java.util.Constant;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    public interface Delegate{
        void onClick(Data_ data_);
    }

    List<Child> childList;
    Context applicationContext;
    Delegate delegate;

    public RVAdapter(List<Child> childList, Context applicationContext, Delegate delegate) {
        this.childList = childList;
        this.applicationContext = applicationContext;
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String text = (childList.get(position).getData().getAuthor());
        holder.username.setText(text);
        holder.comments.setText(applicationContext.getString(R.string.comments,childList.get(position).getData().getNumComments()));
        holder.ups.setText(applicationContext.getString(R.string.ups,childList.get(position).getData().getUps()));
        holder.downs.setText(applicationContext.getString(R.string.downs,childList.get(position).getData().getDowns()));
        Log.d("TAG_X", "onBindViewHolder: "+String.format(Constant.BASE_URL_IMAGE,text));
        Glide.with(applicationContext)
                .load(childList.get(position).getData().getThumbnail())
                .placeholder(R.drawable.user_icon)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.icon);
        holder.content.setText(childList.get(position).getData().getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onClick(childList.get(position).getData());
            }
        });
    }

    @Override
    public int getItemCount() {
        return childList.size() ;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView username;
        TextView content;
        TextView comments;
        TextView ups;
        TextView downs;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.user_icon);
            username = itemView.findViewById(R.id.user_id);
            content = itemView.findViewById(R.id.content);
            ups = itemView.findViewById(R.id.ups);
            downs = itemView.findViewById(R.id.downs);
            comments = itemView.findViewById(R.id.comments);
        }
    }
}
