package com.example.khanh.listenwritedemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.khanh.listenwritedemo.R;
import com.example.khanh.listenwritedemo.helper.AppUtils;
import com.example.khanh.listenwritedemo.adapter.ApplicationsAdapter;
import com.example.khanh.listenwritedemo.module.Config;
import com.example.khanh.listenwritedemo.module.Section;
import com.example.khanh.listenwritedemo.request.TaskConfig;
import com.example.khanh.listenwritedemo.request.TaskSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 7/28/2017.
 */

public class FragmentApplications extends FragmentBase implements ApplicationsAdapter.CallBack{
    @InjectView(R.id.rv_applications)
    RecyclerView rv_applications;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    private ApplicationsAdapter mAdapter;
    Config config;
    @Override
    protected void initDataDefault() {
        super.initDataDefault();
        toolbar.setTitle("Our Applications");
        toolbar.setNavigationIcon(R.drawable.ic_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onBackPressed();
            }
        });
        loadData();
    }
    private void loadList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_applications.setLayoutManager(layoutManager);

        mAdapter = new ApplicationsAdapter(config, this);

        rv_applications.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        TaskConfig taskQuestion = new TaskConfig(getContext());
        taskQuestion.request(new Response.Listener<Config>() {
            @Override
            public void onResponse(Config response) {
                config=response;
                loadList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_applications;
    }
    public static FragmentApplications newInstance(int index) {

        FragmentApplications fragmentResult = new FragmentApplications();
        Bundle bundle = new Bundle();

        bundle.putInt("INDEX",index);

        fragmentResult.setArguments(bundle);
        return fragmentResult;
    }

    @Override
    public void OnClick(int indexx) {
        AppUtils.openApp(mainActivity,config.getRcmApp().get(indexx).getPid());
    }
}
