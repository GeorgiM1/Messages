package com.example.android.messages.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.messages.Models.MsgModel;
import com.example.android.messages.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pc on 4/6/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    Context context;
    ArrayList<MsgModel> msgModels;

    public CustomAdapter (Context context, ArrayList<MsgModel>msgModels){
        this.context=context;
        this.msgModels=msgModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.account_number)
        TextView accNum;
        @BindView(R.id.time_of_msg)
        TextView timeOfMsg;
        @BindView(R.id.message_txt)
        TextView msgTxt;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
