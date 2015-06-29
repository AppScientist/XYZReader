package com.krypto.xyzreader;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.BindBool;
import butterknife.BindString;
import butterknife.ButterKnife;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.GET;


public class MainActivity extends AppCompatActivity{

    @Bind(R.id.app_bar)
    Toolbar mToolbar;
    @Bind(R.id.toolbarTitle)
    TextView mTitle;
    @Bind(R.id.recycleView)
    RecyclerView mRecyclerView;
    @BindBool(R.bool.Land)
    boolean isLand;
    @BindBool(R.bool.tablet)
    boolean isTablet;
    @BindString(R.string.app_name)
    String name;
    @BindString(R.string.roboto_medium)
    String roboto_medium;

    private static CustomRecyclerViewAdapter sAdapter;
    private static final String SAVE="save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitle.setTypeface(Utility.getFont(this, roboto_medium));
        mTitle.setText(name);

        sAdapter = new CustomRecyclerViewAdapter(this);

        if (!isTablet) {

            if (isLand) {
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            } else {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            }

        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        mRecyclerView.setAdapter(sAdapter);

        if (savedInstanceState == null) {
            Intent serviceIntent = new Intent(this, DownloadService.class);
            startService(serviceIntent);
        } else {
            ArrayList<Pojo> results = savedInstanceState.getParcelableArrayList(SAVE);
            sAdapter.setData(results);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<Pojo> list =(ArrayList) sAdapter.getData();
        outState.putParcelableArrayList(SAVE, list);
        super.onSaveInstanceState(outState);
    }

    public interface GetDetails {

        @GET("/u/231329/xyzreader_data/data.json")
        Pojo[] getResults();
    }

    public static class DownloadService extends IntentService {

        public DownloadService() {
            super("download");
        }

        @Override
        protected void onHandleIntent(Intent intent) {

            GetDetails details;
            RestAdapter restAdapter;

            try {

                restAdapter = new RestAdapter.Builder().setEndpoint("https://dl.dropboxusercontent.com").build();
                details = restAdapter.create(GetDetails.class);
                Pojo[] results = details.getResults();
                List<Pojo> resultList=new ArrayList<>(Arrays.asList(results));
                sAdapter.setData(resultList);

            } catch (RetrofitError e) {
                e.printStackTrace();
            }
        }
    }
}
