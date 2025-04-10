package com.example.organize;

import android.util.Log;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class GeminiManager {
    private static final String TAG = "GeminiManager";
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/";
    private static final String API_KEY = BuildConfig.GEMINI_API_KEY;
    private final GeminiApiService apiService;

    public GeminiManager() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .url(chain.request().url().newBuilder()
                                    .addQueryParameter("key", API_KEY)
                                    .build())
                            .header("Content-Type", "application/json")
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(GeminiApiService.class);
    }

    public void sendMessage(String userMessage, String userName, ResponseCallback callback) {
        try {
            String instructions = "Você é o Orga, assistente de organização. Regras:\n" +

                    (userName != null ?
                            "1. USE O NOME '" + userName + "' APENAS NESTA RESPOSTA\n" :
                            "2. NÃO USE NENHUM NOME DE USUÁRIO\n") +
                    "3. Seja sucinto e direto (1-3 frase)\n" +
                    "4. Responda sempre com cordialidade\n\n" +
                    "Pergunta: " + userMessage;

            String jsonBody = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}",
                    instructions.replace("\"", "\\\""));

            RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));

            apiService.getGeminiResponse(body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            String responseText = parseResponse(response.body().string());
                            callback.onSuccess(responseText);
                        } else {
                            callback.onError("Erro na API: " + response.code());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Erro ao processar resposta", e);
                        callback.onError("Erro no processamento");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "Falha na conexão", t);
                    callback.onError("Falha na conexão");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Erro ao criar requisição", e);
            callback.onError("Erro na requisição");
        }
    }

    private String parseResponse(String jsonResponse) throws JSONException {
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray candidates = json.getJSONArray("candidates");
        if (candidates.length() > 0) {
            return candidates.getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        }
        return "Não entendi. Pode repetir?";
    }

    public interface ResponseCallback {
        void onSuccess(String response);
        void onError(String error);
    }
}
