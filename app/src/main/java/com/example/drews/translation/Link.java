package com.example.drews.translation;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Drews on 23.12.2017.
 */

public interface Link {
    @FormUrlEncoded
    @POST("api/v1.5/tr.json/translate")
    Call<Object> translate(@FieldMap Map<String, String> map);
}
