package com.nrs.nsnik.goodasync.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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


    private static final String LOG_TAG = SbUserListFragment.class.getSimpleName();
    @BindView(R.id.sbUserList)
    RecyclerView mSbUsrList;
    @BindView(R.id.sbUserSwipe)
    SwipeRefreshLayout mRefreshList;
    @BindView(R.id.emptyText)
    TextView mEmptyListText;
    List<ShelBeeUserObject> mUserList;
    private Unbinder mUnbinder;
    private SbUsrLsAdapter mAdapter;

    public SbUserListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sb_user_list, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        initialize();
        listeners();
        return v;
    }

    private void initialize() {
        mUserList = new ArrayList<>();
        mSbUsrList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SbUsrLsAdapter(getActivity(), mUserList);
        mSbUsrList.setAdapter(mAdapter);
        mSbUsrList.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mSbUsrList.addItemDecoration(itemDecoration);
        mRefreshList.setRefreshing(true);
        getUserList();
    }

    private void listeners() {
        mRefreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clearList();
                mUserList.clear();
                getUserList();
            }
        });
    }

    private void getUserList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getActivity().getResources().getString(R.string.urlShelfBeeBase))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SbInterface api = retrofit.create(SbInterface.class);
        Call<List<ShelBeeUserObject>> call = api.getUserList();
        call.enqueue(new Callback<List<ShelBeeUserObject>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShelBeeUserObject>> call, @NonNull Response<List<ShelBeeUserObject>> response) {
                if (response.body().size() <= 0) {
                    setEmpty();
                } else {
                    mRefreshList.setRefreshing(false);
                    mUserList.addAll(response.body());
                    mAdapter.updateList(mUserList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ShelBeeUserObject>> call, @NonNull Throwable t) {
                Log.d(LOG_TAG, t.toString());
            }
        });
    }

    private void setEmpty() {
        mSbUsrList.setVisibility(View.GONE);
        mEmptyListText.setVisibility(View.VISIBLE);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);
        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        }
        if (animation != null) {
            getView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    getView().setLayerType(View.LAYER_TYPE_NONE, null);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        return animation;
    }


    @Override
    public void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
