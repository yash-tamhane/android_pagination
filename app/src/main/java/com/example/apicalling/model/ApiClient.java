package com.example.apicalling.model;


import com.example.apicalling.pojo.Data;
import com.example.apicalling.pojo.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    String BASE_URL = "https://reqres.in/api/";

    @GET("users")
    Call<Root> getUserList(@Query("page") int pageIndex,
                           @Query("per_page") int pagesize);
}
