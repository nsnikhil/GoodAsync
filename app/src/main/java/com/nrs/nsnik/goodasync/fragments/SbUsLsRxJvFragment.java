package com.nrs.nsnik.goodasync.fragments;


import android.os.Bundle;
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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SbUsLsRxJvFragment extends android.support.v4.app.Fragment {


    private static final String LOG_TAG = SbUsLsRxJvFragment.class.getSimpleName();
    @BindView(R.id.sbUserList)
    RecyclerView mSbUsrList;
    @BindView(R.id.sbUserSwipe)
    SwipeRefreshLayout mRefreshList;
    @BindView(R.id.emptyText)
    TextView mEmptyListText;
    List<ShelBeeUserObject> mUserList;
    Retrofit mRetrofit;
    private Unbinder mUnbinder;
    private SbUsrLsAdapter mAdapter;

    public SbUsLsRxJvFragment() {
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
        getListRxAndroid();
    }

    private void getListRxAndroid() {
        SbInterface api = getClient().create(SbInterface.class);
        api.getUserListRx().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ShelBeeUserObject>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<ShelBeeUserObject> list) {
                        if (list.size() <= 0) {
                            setEmpty();
                        } else {
                            mRefreshList.setRefreshing(false);
                            mAdapter.updateList(list);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getListRx() {
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
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(getActivity().getResources().getString(R.string.urlShelfBeeBase))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private void setEmpty() {
        mSbUsrList.setVisibility(View.GONE);
        mEmptyListText.setVisibility(View.VISIBLE);
    }

    private void listeners() {
        mRefreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clearList();
                mUserList.clear();
                getListRxAndroid();
            }
        });
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
