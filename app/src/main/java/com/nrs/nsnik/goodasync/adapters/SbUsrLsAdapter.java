package com.nrs.nsnik.goodasync.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nrs.nsnik.goodasync.R;
import com.nrs.nsnik.goodasync.objects.ShelBeeUserObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SbUsrLsAdapter extends RecyclerView.Adapter<SbUsrLsAdapter.MyViewHolder>{

    private Context mContext;
    private List<ShelBeeUserObject> mList;

    public SbUsrLsAdapter(Context context, List<ShelBeeUserObject> list){
        mContext = context;
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemHeading) TextView itemHeading;
        @BindView(R.id.itemText) TextView itemText;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
