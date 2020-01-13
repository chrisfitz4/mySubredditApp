package com.example.android.reddit_java.view;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.reddit_java.R;
import com.example.android.reddit_java.model.Child;
import com.example.android.reddit_java.model.Data_;
import com.example.android.reddit_java.util.Constant;

public class SubredditFragment extends Fragment {

    Button cancel;
    ImageView copy;
    ImageView mail;
    Data_ data_;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cancel = view.findViewById(R.id.cancel_button);
        copy = view.findViewById(R.id.copy_button);
        mail = view.findViewById(R.id.email_button);
        Bundle bundle = getArguments();
        data_ = bundle.getParcelable(Constant.DATA_KEY);
        if(data_==null){
            Log.d("TAG_X", "onViewCreated: data is null");
        }else{
            Log.d("TAG_X", "onViewCreated: data is good");
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetach();
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data =  ClipData.newPlainText("text",data_.getTitle());
                manager.setPrimaryClip(data);
                Log.d("TAG_X", "onClick: "+data_.getTitle());
            }
        });
        mail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String toSend = "Check out what %s said on Reddit: \"%s\"";
                toSend = String.format(toSend,data_.getAuthor(),data_.getTitle());
                Log.d("TAG_X", "onClick: "+toSend);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                //intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_TEXT,toSend);
                try {
                    startActivity(Intent.createChooser(intent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try{
            getFragmentManager().popBackStack();
        }catch(NullPointerException n){
            Log.d("TAG_X", "onDetach: "+n.getMessage());
        }
    }
}
