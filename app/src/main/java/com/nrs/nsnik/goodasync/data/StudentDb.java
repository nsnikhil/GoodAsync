package com.nrs.nsnik.goodasync.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nrs.nsnik.goodasync.interfaces.StudentDao;

@Database(entities = {StudentPojo.class},version = 4)
public abstract class StudentDb extends RoomDatabase{
    private static StudentDb mInstance;
    public abstract StudentDao studentDao();

    public static StudentDb getStudentDatabase(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),StudentDb.class,"StudentPojo").build();
        }
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }
}
