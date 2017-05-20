package com.nrs.nsnik.goodasync.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.nrs.nsnik.goodasync.R;
import com.nrs.nsnik.goodasync.adapters.SbUsrLsAdapter;
import com.nrs.nsnik.goodasync.interfaces.SbInterface;
import com.nrs.nsnik.goodasync.objects.ShelBeeUserObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SbUserListFragment extends android.support.v4.app.Fragment {


    @BindView(R.id.sbUserList) RecyclerView mSbUsrList;
    @BindView(R.id.sbUserSwipe) SwipeRefreshLayout mRefreshList;
    private static final String LOG_TAG = SbUserListFragment.class.getSimpleName();
    List<ShelBeeUserObject> mUserList;
    private Unbinder mUnbinder;
    SbUsrLsAdapter mAdapter;

    public SbUserListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sb_user_list, container, false);
        mUnbinder = ButterKnife.bind(this,v);
        initialize();
        listeners();
        return v;
    }

    private void initialize() {
        mUserList = new ArrayList<>();
        mSbUsrList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SbUsrLsAdapter(getActivity(),mUserList);
        mSbUsrList.setAdapter(mAdapter);
        mRefreshList.setRefreshing(true);
        getUserList();
    }

    private void listeners() {
        mRefreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserList.clear();
                mSbUsrList.setAdapter(null);
                getUserList();
            }
        });
    }

    private void getUserList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getActivity().getResources().getString(R.string.urlShelfBeeBase))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SbInterface api = retrofit.create(SbInterface.class);
        Call<List<ShelBeeUserObject>> call = api.getUserList();
        call.enqueue(new Callback<List<ShelBeeUserObject>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShelBeeUserObject>> call, @NonNull Response<List<ShelBeeUserObject>> response) {
                mRefreshList.setRefreshing(false);
                mUserList.addAll(response.body());
                mAdapter = new SbUsrLsAdapter(getActivity(), mUserList);
                mSbUsrList.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<ShelBeeUserObject>> call, @NonNull Throwable t) {
                Log.d(LOG_TAG,t.toString());
            }
        });
    }


    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
