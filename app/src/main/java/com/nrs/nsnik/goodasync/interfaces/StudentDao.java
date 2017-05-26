package com.nrs.nsnik.goodasync.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import android.arch.persistence.room.Query;

import com.nrs.nsnik.goodasync.data.StudentPojo;
import com.nrs.nsnik.goodasync.objects.StudentObject;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudentData(StudentPojo... objects);

    @Query("SELECT * FROM StudentPojo")
    List<StudentPojo> getAllStudents();

}
