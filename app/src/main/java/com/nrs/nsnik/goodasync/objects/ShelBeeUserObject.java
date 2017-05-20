package com.nrs.nsnik.goodasync.objects;

import com.google.gson.annotations.SerializedName;

public class ShelBeeUserObject {

    @SerializedName("uid")
    private String mUid;

    @SerializedName("phoneno")
    private String mPhone;

    @SerializedName("name")
    private String mName;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("fkey")
    private String mFireKey;

    @SerializedName("bstatus")
    private int mBanStatus;


    public String getmUid() {
        return mUid;
    }

    public String getmName() {
        return mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmFireKey() {
        return mFireKey;
    }

    public int getmBanStatus() {
        return mBanStatus;
    }
}
