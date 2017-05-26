package com.nrs.nsnik.goodasync.interfaces;

import com.nrs.nsnik.goodasync.objects.ShelBeeUserObject;
import com.nrs.nsnik.goodasync.objects.StudentObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SbInterface {

    @GET("/booktrade/userQueryEvery.php")
    Call<List<ShelBeeUserObject>> getUserList();

    @GET("/booktrade/userQueryAll.php")
    Call<List<ShelBeeUserObject>> getSingleUser(@Query("uid") String userId);

    @GET("/booktrade/userQueryEvery.php")
        //DisposableObserver <List<ShelBeeUserObject>> getUserListRx();
    Observable<List<ShelBeeUserObject>> getUserListRx();

    @GET("/booktrade/userQueryAll.php")
    Observable<List<ShelBeeUserObject>> getSingleUserRx(@Query("uid") String userId);

    @FormUrlEncoded
    @POST("/bus_tracking_ws/student_data.php")
    Observable<List<StudentObject>> getStudentList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/bus_tracking_ws/student_data.php")
    Observable<String> getStudentString(@FieldMap Map<String, String> params);

}
