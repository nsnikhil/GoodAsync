package com.nrs.nsnik.goodasync.objects;

public class ShelBeeUserObject {

    private String mUid,mPhone,mName,mAddress,mFireKey;
    private int mBanStatus;

    public ShelBeeUserObject(String uid,String name,String phone,String address,String fireKey,int banStatus){
        mUid = uid;
        mName = name;
        mPhone = phone;
        mAddress = address;
        mFireKey = fireKey;
        mBanStatus = banStatus;
    }

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
