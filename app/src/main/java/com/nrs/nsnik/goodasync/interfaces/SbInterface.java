package com.nrs.nsnik.goodasync.interfaces;

import com.nrs.nsnik.goodasync.objects.ShelBeeUserObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SbInterface {

    @GET("/booktrade/userQueryEvery.php")
    Call<List<ShelBeeUserObject>> getUserList();

    @GET("/booktrade/userQueryAll.php")
    Call<List<ShelBeeUserObject>> getSingleUser(@Query("uid") String userId);

}
