package com.example.android.messages;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.messages.Adapters.CustomAdapter;
import com.example.android.messages.Api.RestApi;
import com.example.android.messages.Fragments.SetupFragment;
import com.example.android.messages.Models.MsgModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessagesActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    FloatingActionButton fab;
    boolean transactionToSync = false;

    ArrayList<MsgModel> msgModel;
    CustomAdapter mCustomAdapter;
    RestApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.settings));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.getOverflowIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);







        ButterKnife.bind(this);
        api = new RestApi(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mCustomAdapter = new CustomAdapter(this, msgModel);
        mRecyclerView.setAdapter(mCustomAdapter);
        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<ArrayList<MsgModel>> call = api.getMsgs();
                call.enqueue(new Callback<ArrayList<MsgModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MsgModel>> call, Response<ArrayList<MsgModel>> response) {
                        if (response.isSuccessful()) {
                            msgModel = response.body();
                            mCustomAdapter = new CustomAdapter(MessagesActivity.this, msgModel);
                            mRecyclerView.setAdapter(mCustomAdapter);
                            Toast.makeText(MessagesActivity.this, "Response successful!", Toast.LENGTH_LONG).show();
//                            PreferencesManager.addPhoneNumber(msgModel.get(1).getPhone_ID(), MessagesActivity.this);
//                            PreferencesManager.addTxtMsg(msgModel.get(1).getNotice_text(), MessagesActivity.this);
//                            PreferencesManager.addDate(msgModel.get(1).getDatetime(),MessagesActivity.this);

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<MsgModel>> call, Throwable t) {

                    }
                });
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessagesActivity.this, AddMessageActivity.class));

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.placeholder_messagesActivity, new SetupFragment());
        transaction.addToBackStack(null);
        transaction.commit();




        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
