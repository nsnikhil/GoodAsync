package com.nrs.nsnik.goodasync.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nrs.nsnik.goodasync.R;
import com.nrs.nsnik.goodasync.adapters.SbUsrLsAdapter;
import com.nrs.nsnik.goodasync.interfaces.SbInterface;
import com.nrs.nsnik.goodasync.objects.ShelBeeUserObject;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SbUsLsRxJvFragment extends android.support.v4.app.Fragment {


    @BindView(R.id.sbUserList) RecyclerView mSbUsrList;
    @BindView(R.id.sbUserSwipe) SwipeRefreshLayout mRefreshList;
    @BindView(R.id.emptyText) TextView mEmptyListText;
    private static final String LOG_TAG = SbUsLsRxJvFragment.class.getSimpleName();
    List<ShelBeeUserObject> mUserList;
    private Unbinder mUnbinder;
    private SbUsrLsAdapter mAdapter;
    Retrofit mRetrofit;
    //private Observable<ShelBeeUserObject> mObservable;

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
        mSbUsrList.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mSbUsrList.addItemDecoration(itemDecoration);
        mRefreshList.setRefreshing(true);
        getListRxAndroid();
    }

    private void getListRxAndroid(){
        SbInterface api = getClient().create(SbInterface.class);
        api.getUserListRx().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ShelBeeUserObject>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<ShelBeeUserObject> list) {
                        mRefreshList.setRefreshing(false);
                        mAdapter.updateList(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(LOG_TAG,e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getListRx(){
        Observable<ShelBeeUserObject> observable = Observable.create(new ObservableOnSubscribe<ShelBeeUserObject>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ShelBeeUserObject> objectObservableEmitter) throws Exception {

            }
        });

        Observer<ShelBeeUserObject> observer = new Observer<ShelBeeUserObject>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(LOG_TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(ShelBeeUserObject value) {
                Log.e(LOG_TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(LOG_TAG, "onComplete: All Done!");
            }
        };
        observable.subscribeOn(Schedulers.newThread());
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);
    }


    public Retrofit getClient() {
        if (mRetrofit==null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(getActivity().getResources().getString(R.string.urlShelfBeeBase))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private void setEmpty(){
        mSbUsrList.setVisibility(View.GONE);
        mEmptyListText.setVisibility(View.VISIBLE);
    }

    private void listeners(){
        mRefreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserList.clear();
                mSbUsrList.setAdapter(null);
                getListRxAndroid();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
