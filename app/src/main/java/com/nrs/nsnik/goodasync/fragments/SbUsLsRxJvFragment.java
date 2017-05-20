package com.nrs.nsnik.goodasync.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nrs.nsnik.goodasync.R;
import com.nrs.nsnik.goodasync.adapters.SbUsrLsAdapter;
import com.nrs.nsnik.goodasync.objects.ShelBeeUserObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SbUsLsRxJvFragment extends android.support.v4.app.Fragment {


    @BindView(R.id.sbUserList) RecyclerView mSbUsrList;
    @BindView(R.id.sbUserSwipe) SwipeRefreshLayout mRefreshList;
    @BindView(R.id.emptyText) TextView mEmptyListText;
    private static final String LOG_TAG = SbUsLsRxJvFragment.class.getSimpleName();
    List<ShelBeeUserObject> mUserList;
    private Unbinder mUnbinder;
    private SbUsrLsAdapter mAdapter;

    public SbUsLsRxJvFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sb_user_list, container, false);
        mUnbinder = ButterKnife.bind(this,v);
        initialize();
        listeners();
        return v;
    }

    private void initialize(){
        mUserList = new ArrayList<>();
        mSbUsrList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SbUsrLsAdapter(getActivity(),mUserList);
        mSbUsrList.setAdapter(mAdapter);
        setEmpty();
    }

    private void setEmpty(){
        mSbUsrList.setVisibility(View.GONE);
        mEmptyListText.setVisibility(View.VISIBLE);
    }

    private void listeners(){

    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
