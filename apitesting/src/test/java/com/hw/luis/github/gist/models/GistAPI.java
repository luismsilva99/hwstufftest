package com.hw.luis.github.gist.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;
import retrofit.Call;
import retrofit.http.*;

/**
 * GistAPI interface.
 */
public interface GistAPI {

    String ENDPOINT = "https://api.github.com";
    String AUTH_TOKEN = "";
    String USER = "";

    @POST("gists")
    Call<JsonObject> createGist(@Header("Authorization") String authToken, @Body CreateRequest createRequest);

    @GET("/users/{user}/gists")
    Call<JsonArray> getUserGists(@Path("user") String user);

    @GET("/users/{user}/gists")
    Call<JsonArray> getUserGists(@Header("Authorization") String authToken, @Path("user") String user);

    @GET("/gists/public")
    Call<ResponseBody> getPublicGists();

    @DELETE("/gists/{id}")
    Call<ResponseBody> deleteGist(@Header("Authorization") String authToken, @Path("id") String gistId);


}
