package com.nrs.nsnik.goodasync.interfaces;

import com.nrs.nsnik.goodasync.objects.ShelBeeUserObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface SbUserInterface {

    @GET("/booktrade/userQueryEvery.php")
    Call<List<ShelBeeUserObject>> getUserList();

}
