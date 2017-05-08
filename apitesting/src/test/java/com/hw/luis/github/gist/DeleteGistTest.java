package com.hw.luis.github.gist;


import com.google.gson.*;
import com.hw.luis.github.gist.models.CreateRequest;
import com.hw.luis.github.gist.models.File;
import com.hw.luis.github.gist.models.GistAPI;
import com.squareup.okhttp.ResponseBody;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class DeleteGistTest {

    private static File fileA;
    private static File fileB;
    private static HashMap<String, File> files;
    private static Gson gson;
    private static Retrofit retrofit;
    private static GistAPI githubUserAPI;

    @BeforeClass
    public static void onlyOnce() {
        files = new HashMap<String, File>(2);
        fileA = new File("A.txt", "A content");
        fileB = new File("B.txt", "B content");

        files.put(fileA.getName(), fileA);
        files.put(fileB.getName(), fileB);

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(GistAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        githubUserAPI = retrofit.create(GistAPI.class);
    }

    @Test
    public void deleteGistTest() throws IOException, InterruptedException {
        CreateRequest cr = new CreateRequest("This is a test Gist", true, files);
        Call<JsonObject> createGistCall = githubUserAPI.createGist(GistAPI.AUTH_TOKEN, cr);

        Response<JsonObject> createResponse = createGistCall.execute();
        Assert.assertEquals(createResponse.headers().get("Status"), "201 Created");

        JsonObject jsonResponse = createResponse.body();
        String gistId = jsonResponse.get("id").getAsString();

        Call<ResponseBody> deleteGistCall = githubUserAPI.deleteGist(githubUserAPI.AUTH_TOKEN, gistId);
        Response<ResponseBody> deleteResponse = deleteGistCall.execute();
        Assert.assertEquals("204 No Content", deleteResponse.headers().get("Status"));

        // sometimes the created gist doesn't come listed, may be beacause the calls are too close, so wait a little bit
        Thread.sleep(2000);

        Response<JsonArray> listUserGists = githubUserAPI.getUserGists(GistAPI.USER).execute();
        Iterator<JsonElement> it = listUserGists.body().iterator();
        boolean foundCreatedGist = false;
        while (it.hasNext()) {
            JsonObject gistObject = it.next().getAsJsonObject();
            String gistObjectId = gistObject.get("id").getAsString();

            if (gistId.equals(gistObjectId)) {
                foundCreatedGist = true;
                break;
            }
        }
        Assert.assertFalse(foundCreatedGist);
    }


    @Test
    public void deleteGistWithInvalidTokenTest() throws IOException, InterruptedException {
        CreateRequest cr = new CreateRequest("This is a test Gist", true, files);
        Call<JsonObject> createGistCall = githubUserAPI.createGist(GistAPI.AUTH_TOKEN, cr);

        Response<JsonObject> createResponse = createGistCall.execute();
        Assert.assertEquals(createResponse.headers().get("Status"), "201 Created");

        JsonObject jsonResponse = createResponse.body();
        String gistId = jsonResponse.get("id").getAsString();

        Call<ResponseBody> deleteGistCall = githubUserAPI.deleteGist("invalid token :)", gistId);
        Response<ResponseBody> deleteResponse = deleteGistCall.execute();
        Assert.assertEquals("401 Unauthorized", deleteResponse.headers().get("Status"));

        // sometimes the created gist doesn't come listed, may be beacause the calls are too close, so wait a little bit
        Thread.sleep(2000);

        Response<JsonArray> listUserGists = githubUserAPI.getUserGists(GistAPI.USER).execute();
        Iterator<JsonElement> it = listUserGists.body().iterator();
        boolean foundCreatedGist = false;
        while (it.hasNext()) {
            JsonObject gistObject = it.next().getAsJsonObject();
            String gistObjectId = gistObject.get("id").getAsString();

            if (gistId.equals(gistObjectId)) {
                foundCreatedGist = true;
                break;
            }
        }
        Assert.assertTrue(foundCreatedGist);
    }
}
