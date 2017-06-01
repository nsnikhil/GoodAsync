package com.nrs.nsnik.goodasync.fragments;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.app.Fragment;
import android.test.suitebuilder.TestMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nrs.nsnik.goodasync.R;
import com.nrs.nsnik.goodasync.objects.ShelBeeUserObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ViewModelFragment extends LifecycleFragment {

    private Unbinder mUnbinder;
    private static final String TAG = ViewModelFragment.class.getSimpleName();

    public ViewModelFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_view_model, container, false);
        mUnbinder = ButterKnife.bind(this,v);
        initialize();
        listeners();
        TestModel model = ViewModelProviders.of(this).get(TestModel.class);

        getLifecycle().addObserver(new TestObserver());
        return v;
    }

    private void initialize(){
    }

    private void listeners(){
    }

    private void cleanUp(){
        if(mUnbinder!=null){
            mUnbinder.unbind();
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

    private class TestObserver implements LifecycleObserver{
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void onResume(){

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void onPause(){

        }
    }

    private class TestModel extends ViewModel{

        LiveData<List<ShelBeeUserObject>> userList = new  MutableLiveData<>();

        LiveData<List<ShelBeeUserObject>> getUserList(){
            return null;
        }
    }

}
