package com.nrs.nsnik.goodasync.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nrs.nsnik.goodasync.R;
import com.nrs.nsnik.goodasync.adapters.SbUsrLsAdapter;
import com.nrs.nsnik.goodasync.interfaces.SbUserInterface;
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
        getUserList();
    }

    private void listeners() {
    }

    private void getUserList(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getActivity().getResources().getString(R.string.urlShelfBeeBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        SbUserInterface api = retrofit.create(SbUserInterface.class);
        Call<List<ShelBeeUserObject>> call = api.getUserList();
        call.enqueue(new Callback<List<ShelBeeUserObject>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShelBeeUserObject>> call, @NonNull Response<List<ShelBeeUserObject>> response) {

            }

            @Override
            public void onFailure(@NonNull Call<List<ShelBeeUserObject>> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
