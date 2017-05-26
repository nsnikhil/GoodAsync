package com.nrs.nsnik.goodasync.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class StudentPojo {

    public String studentName;
    public String contactNo;
    @PrimaryKey
    public String StudentId;
    public String photoUrl;

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getStudentId() {
        return StudentId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
