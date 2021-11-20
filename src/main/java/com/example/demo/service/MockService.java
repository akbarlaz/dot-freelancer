package com.example.demo.service;

import com.example.demo.model.mock.MockModel;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface MockService {
    @GET("users")
    Call<List<MockModel>> browse();
}