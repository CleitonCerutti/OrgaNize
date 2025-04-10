package com.example.organize;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GeminiApiService {
    @POST("models/gemini-2.0-flash:generateContent")
    Call<ResponseBody> getGeminiResponse(
            @Body RequestBody requestBody
    );
}
