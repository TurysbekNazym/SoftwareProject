package com.example.admin.comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 06.10.2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<UserInfo> userList;
    private Context context;


    public Adapter(List<UserInfo> userList) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activitydetailadd,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserInfo det = userList.get(position);
        holder.name.setText(det.getUsername());
        holder.text.setText(det.getComment());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.user);
            text = (TextView) itemView.findViewById(R.id.comm);
        }
    }





}
