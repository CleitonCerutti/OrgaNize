package com.example.organize;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GeminiManager {
    private static final String API_KEY = "AIzaSyBwWrXTyRHkqT-I_LozGSKvGMhdAbBXUYo"; // Substitua pela sua chave
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
//
//    public String sendMessage(String userMessage) {
//        try {
//            // Criar o corpo da requisição
//            JSONObject jsonBody = new JSONObject();
//            JSONArray contentsArray = new JSONArray();
//            JSONObject contentObject = new JSONObject();
//            JSONArray partsArray = new JSONArray();
//            JSONObject partObject = new JSONObject();
//
//            partObject.put("text", userMessage); // A mensagem do usuário
//            partsArray.put(partObject);
//            contentObject.put("parts", partsArray);
//            contentsArray.put(contentObject);
//            jsonBody.put("contents", contentsArray);
//
//            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
//
//            // Criar a requisição
//            Request request = new Request.Builder()
//                    .url(API_URL + "?key=" + API_KEY) // Adiciona a chave de API como parâmetro
//                    .post(body)
//                    .addHeader("Content-Type", "application/json")
//                    .build();
//
//            // Executar a requisição
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful() && response.body() != null) {
//                String responseBody = response.body().string();
//                JSONObject jsonResponse = new JSONObject(responseBody);
//                JSONArray candidates = jsonResponse.getJSONArray("candidates");
//                if (candidates.length() > 0) {
//                    JSONObject candidate = candidates.getJSONObject(0);
//                    JSONObject content = candidate.getJSONObject("content");
//                    JSONArray parts = content.getJSONArray("parts");
//                    if (parts.length() > 0) {
//                        JSONObject part = parts.getJSONObject(0);
//                        return part.optString("text", "Nenhuma resposta foi gerada.");
//                    }
//                }
//            } else {
//                Log.e("GeminiManager", "Erro na requisição: " + response.code());
//            }
//        } catch (IOException | JSONException e) {
//            Log.e("GeminiManager", "Erro ao enviar mensagem: " + e.getMessage());
//        }
//        return "Erro ao processar a mensagem.";
//    }
}
