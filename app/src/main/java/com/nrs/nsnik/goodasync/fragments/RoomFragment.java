package com.nrs.nsnik.goodasync.fragments;


import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.nrs.nsnik.goodasync.R;
import com.nrs.nsnik.goodasync.data.StudentDb;
import com.nrs.nsnik.goodasync.data.StudentPojo;
import com.nrs.nsnik.goodasync.interfaces.SbInterface;
import com.nrs.nsnik.goodasync.objects.StudentObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.completable.CompletableSubscribeOn;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.CompletableSubscriber;
import rx.Subscription;
import rx.functions.Action1;

public class RoomFragment extends android.support.v4.app.Fragment {

    @BindView(R.id.roomTempAdd)Button mRoomTempAdd;
    @BindView(R.id.roomTempShow)Button mRoomTempShow;
    private Unbinder mUnbinder;
    private static final String TAG = RoomFragment.class.getSimpleName();
    private CompositeDisposable mDisposable;
    private Retrofit mRetrofit;

    public RoomFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  =  inflater.inflate(R.layout.fragment_room, container, false);
        mUnbinder = ButterKnife.bind(this,v);
        initialize();
        listeners();
        return v;
    }

    private void initialize(){
        mDisposable = new CompositeDisposable();
    }

    private void listeners(){
        mRoomTempAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStudentListDrivool();
            }
        });
        mRoomTempShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new queryDatabase().execute();
            }
        });
    }

    private Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("gapn", "mum-intel-stmary");
        params.put("route_id", "bus16smsicse");
        return params;
    }

    private void getStudentListDrivool() {
        SbInterface api = getStudentClient().create(SbInterface.class);
        mDisposable.add(api.getStudentList(getParams()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StudentObject>>() {
                    @Override
                    public void accept(@NonNull List<StudentObject> studentObjects) throws Exception {
                        new addToDatabase(studentObjects).execute();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, throwable.toString());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                }));

    }

    private class queryDatabase extends AsyncTask<Void,Void,List<StudentPojo>>{

        @Override
        protected List<StudentPojo> doInBackground(Void... params) {
            return StudentDb.getStudentDatabase(getActivity()).studentDao().getAllStudents();
        }

        @Override
        protected void onPostExecute(List<StudentPojo> studentPojos) {
            super.onPostExecute(studentPojos);
            for(StudentPojo pojo:studentPojos){
                Log.d(TAG, pojo.getStudentName());
            }
        }
    }

    private class addToDatabase extends AsyncTask<Void,Void,Void> {

        private List<StudentObject> list;

        addToDatabase(List<StudentObject> list){
            this.list = list;
        }

        @Override
        protected Void doInBackground(Void... params) {
            StudentPojo pojo = new StudentPojo();
            if(list.size()>0){
                for(int i=0;i<list.size();i++){
                    StudentObject object = list.get(i);
                    pojo.setStudentName(object.getmStudentName());
                    pojo.setContactNo(object.getmContactNo());
                    pojo.setStudentId(object.getmStudentId());
                    pojo.setPhotoUrl(object.getmPhotoUrl());
                    addStudent(StudentDb.getStudentDatabase(getActivity()),pojo);
                }
            }else {
                Log.d(TAG, "Empty Response");
            }
            return null;
        }
    }

    private static void addStudent(StudentDb db, StudentPojo object) {
        db.studentDao().insertStudentData(object);
    }

    public Retrofit getStudentClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://isirs.org/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private void cleanUp(){
        if(mUnbinder!=null){
            mUnbinder.unbind();
        }if(mDisposable!=null){
            mDisposable.dispose();
        }
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
        cleanUp();
        super.onDestroy();
    }

}
