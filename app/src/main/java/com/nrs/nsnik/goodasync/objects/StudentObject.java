package com.nrs.nsnik.goodasync.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 25-May-17.
 */

public class StudentObject {

    @SerializedName("studentName")
    private String mStudentName;

    @SerializedName("contactNumber")
    private String mContactNo;

    @SerializedName("intellinect_id")
    private String mStudentId;

    @SerializedName("photo")
    private String mPhotoUrl;

    public String getmStudentName() {
        return mStudentName;
    }

    public String getmStudentId() {
        return mStudentId;
    }

    public String getmContactNo() {
        return mContactNo;
    }

    public String getmPhotoUrl() {
        return mPhotoUrl;
    }
}
