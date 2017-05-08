package com.hw.luis.github.gist;


import com.google.gson.*;
import com.hw.luis.github.gist.models.CreateRequest;
import com.hw.luis.github.gist.models.File;
import com.hw.luis.github.gist.models.GistAPI;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import retrofit.GsonConverterFactory;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 */
public class CreateGistTest {

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
    public void createPublicGistTest() throws IOException, InterruptedException {
        CreateRequest cr = new CreateRequest("This is a test Gist", true, files);
        Call<JsonObject> createGistCall = githubUserAPI.createGist(GistAPI.AUTH_TOKEN, cr);

        Response<JsonObject> responseBody = createGistCall.execute();
        Assert.assertEquals(responseBody.headers().get("Status"), "201 Created");

        JsonObject jsonResponse = responseBody.body();
        String gistUrl = jsonResponse.get("url").getAsString();

        // sometimes the created gist doesn't come listed, may be beacause the calls are too close, so wait a little bit
        Thread.sleep(2000);

        Response<JsonArray> listUserGists = githubUserAPI.getUserGists(GistAPI.USER).execute();
        Iterator<JsonElement> it = listUserGists.body().iterator();
        boolean foundCreatedGist = false;
        while (it.hasNext()) {
            JsonObject gistObject = it.next().getAsJsonObject();
            String gistObjectUrl = gistObject.get("url").getAsString();

            System.out.println("comparing \n" + gistUrl + "\n" + gistObjectUrl + "\n");
            if (gistUrl.equals(gistObjectUrl)) {
                foundCreatedGist = true;
                break;
            }
        }
        Assert.assertTrue(foundCreatedGist);
    }


    @Test
    public void createPrivateGistTest() throws IOException, InterruptedException {
        CreateRequest cr = new CreateRequest("This is a test Gist", false, files);
        Call<JsonObject> createGistCall = githubUserAPI.createGist(GistAPI.AUTH_TOKEN, cr);

        Response<JsonObject> responseBody = createGistCall.execute();
        Assert.assertEquals(responseBody.headers().get("Status"), "201 Created");

        JsonObject jsonResponse = responseBody.body();
        String gistUrl = jsonResponse.get("url").getAsString();

        // sometimes the created gist doesn't come listed, may be beacause the calls are too close, so wait a little bit
        Thread.sleep(2000);

        // assert that the private Gist isn't listed in the public listing
        Response<JsonArray> listUserGists = githubUserAPI.getUserGists(GistAPI.USER).execute();
        System.out.println(listUserGists.toString());
        Iterator<JsonElement> it = listUserGists.body().iterator();
        boolean foundCreatedGist = false;
        while (it.hasNext()) {
            JsonObject gistObject = it.next().getAsJsonObject();
            String gistObjectUrl = gistObject.get("url").getAsString();

            if (gistUrl.equals(gistObjectUrl)) {
                foundCreatedGist = true;
                break;
            }
        }
        Assert.assertFalse(foundCreatedGist);

        // assert that the private gist is listed in the private listing
        listUserGists = githubUserAPI.getUserGists(GistAPI.AUTH_TOKEN, GistAPI.USER).execute();
        it = listUserGists.body().iterator();
        foundCreatedGist = false;
        while (it.hasNext()) {
            JsonObject gistObject = it.next().getAsJsonObject();
            String gistObjectUrl = gistObject.get("url").getAsString();

            if (gistUrl.equals(gistObjectUrl)) {
                foundCreatedGist = true;
                break;
            }
        }
        Assert.assertTrue(foundCreatedGist);
    }
}
