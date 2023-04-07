package com.example.avante_app;
import com.example.avante_app.DataModels.DataModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("users?page=1&per_page=12")
    Call<DataModel> getApiData();

}