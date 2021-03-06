package com.nrs.nsnik.goodasync.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nrs.nsnik.goodasync.R;
import com.nrs.nsnik.goodasync.objects.ShelBeeUserObject;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SbUsrLsAdapter extends RecyclerView.Adapter<SbUsrLsAdapter.MyViewHolder> {

    private static final String LOG_TAG = SbUsrLsAdapter.class.getSimpleName();
    private static final String[] colorArray = {"#D32F2F", "#C2185B", "#7B1FA2", "#512DA8", "#303F9F", "#1976D2", "#0288D1",
            "#0097A7", "#00796B", "#388E3C", "#689F38", "#AFB42B", "#FBC02D", "#FFA000", "#F57C00", "#E64A19"};
    private Context mContext;
    private List<ShelBeeUserObject> mList;
    private Random r = new Random();

    public SbUsrLsAdapter(Context context, List<ShelBeeUserObject> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ShelBeeUserObject object = mList.get(position);
        holder.itemHeading.setText(object.getmName());
        holder.itemText.setText(object.getmName());
        holder.itemHeading.setBackgroundTintList(stateList());
    }

    public void updateList(List<ShelBeeUserObject> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void clearList() {
        mList.clear();
        notifyDataSetChanged();
    }

    private ShelBeeUserObject getItem(int adapterPosition) {
        return mList.get(adapterPosition);
    }

    private int getRandom() {
        int color = r.nextInt(colorArray.length);
        return Color.parseColor(colorArray[color]);
    }

    private ColorStateList stateList() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_pressed}
        };
        int color = getRandom();
        int[] colors = new int[]{color, color, color, color};
        return new ColorStateList(states, colors);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemHeading)
        TextView itemHeading;
        @BindView(R.id.itemText)
        TextView itemText;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
