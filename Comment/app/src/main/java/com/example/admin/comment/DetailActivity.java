package com.example.admin.comment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private List<UserInfo> userList = new ArrayList<>();
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView send_btn = (TextView) findViewById(R.id.btn_send);
        TextView username = (TextView)findViewById(R.id.username);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOfComment);

        adapter = new Adapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        Bundle extras = getIntent().getExtras();
        final String user_name = extras.getString("message");
        username.setText(user_name);


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText add_comment = (EditText)findViewById(R.id.add_comment);
                final String comment = add_comment.getText().toString();
                UserInfo userDetail = new UserInfo(user_name,comment);
                userList.add(userDetail);
                adapter.notifyDataSetChanged();
            }

        });
    }
}
