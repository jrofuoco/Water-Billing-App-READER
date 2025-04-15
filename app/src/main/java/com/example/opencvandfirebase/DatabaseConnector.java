package com.example.opencvandfirebase;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;

public class DatabaseConnector {

    private static final String SUPABASE_URL = "https://qedemugeyctrrpxkcjpr.supabase.co";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFlZGVtdWdleWN0cnJweGtjanByIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTg0MDM5NjYsImV4cCI6MjAzMzk3OTk2Nn0.tgr8odentTg1nG_7XmAUZG6RknKXfFRoOnAcGH1Tp34";

    // Create OkHttpClient with custom timeout settings
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
            .build();

    // Method to create a new OkHttpClient instance
    public static OkHttpClient getClient() {
        return client;
    }

    // Method to perform GET request
    public static Response performGetRequest(String endpoint) throws IOException {
        String url = SUPABASE_URL + endpoint;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        return client.newCall(request).execute();
    }

    // Method to perform POST request
    public static Response performPostRequest(String endpoint, String json) throws IOException {
        String url = SUPABASE_URL + endpoint;
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );
        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        return client.newCall(request).execute();
    }

    // Method to perform PATCH request
    public static Response performPatchRequest(String endpoint, String json) throws IOException {
        String url = SUPABASE_URL + endpoint;
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );
        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .patch(body)
                .build();

        return client.newCall(request).execute();
    }

    // Method to sign up a new user
    public static Response signUp(String email, String password) throws IOException {
        String endpoint = "/auth/v1/signup";
        String json = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        return performPostRequest(endpoint, json);
    }

    // Method to sign in a user
    public static Response signIn(String email, String password) throws IOException {
        String endpoint = "/auth/v1/token?grant_type=password";
        String json = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        return performPostRequest(endpoint, json);
    }


    // Method to get user info
    public static Response getUserInfo(String accessToken) throws IOException {
        String endpoint = "/auth/v1/user";
        Request request = new Request.Builder()
                .url(SUPABASE_URL + endpoint)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + accessToken)
                .get()
                .build();

        return client.newCall(request).execute();
    }

    // Method to refresh token
    public static Response refreshToken(String refreshToken) throws IOException {
        String endpoint = "/auth/v1/token";
        String json = "{\"grant_type\":\"refresh_token\",\"refresh_token\":\"" + refreshToken + "\"}";
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );
        Request request = new Request.Builder()
                .url(SUPABASE_URL + endpoint)
                .addHeader("apikey", API_KEY)
                .post(body)
                .build();

        return client.newCall(request).execute();
    }
}
