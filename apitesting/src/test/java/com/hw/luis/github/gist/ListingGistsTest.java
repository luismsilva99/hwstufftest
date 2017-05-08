package com.hw.luis.github.gist;

import com.google.gson.*;
import com.hw.luis.github.gist.models.CreateRequest;
import com.hw.luis.github.gist.models.File;
import com.hw.luis.github.gist.models.GistAPI;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import java.io.IOException;
import java.util.*;

public class ListingGistsTest {

    private static File fileA;
    private static File fileB;
    private static HashMap<String, File> files;
    private static Gson gson;
    private static Retrofit retrofit;
    private static GistAPI githubUserAPI;
    private static List<CreateRequest> createRequestList;

    @BeforeClass
    public static void onlyOnce() {
        Random randomGenerator = new Random();
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

        createRequestList = new ArrayList(3);
        for (int i = 0; i < 3; i++) {
            int nrFiles = randomGenerator.nextInt(3) + 1;
            HashMap<String, File> files = new HashMap<String, File>(nrFiles);
            for (int j = 0; j < nrFiles; j++) {
                File f = new File(randomGenerator.nextInt(10000) + ".txt", UUID.randomUUID().toString());
                files.put(f.getName(), f);
            }

            CreateRequest createRequest = new CreateRequest(UUID.randomUUID().toString(), true, files);
            createRequestList.add(createRequest);
        }
    }

    @Test
    public void createPublicGistTest() throws IOException, InterruptedException {
        for (CreateRequest createRequest : createRequestList) {
            Call<JsonObject> createGistCall = githubUserAPI.createGist(GistAPI.AUTH_TOKEN, createRequest);
            Response<JsonObject> responseBody = createGistCall.execute();

            String id = responseBody.body().get("id").getAsString();

            // sometimes the created gist doesn't come listed, may be beacause the calls are too close, so wait a little bit
            Thread.sleep(2000);

            boolean gistValidated = false;
            Response<JsonArray> listUserGists = githubUserAPI.getUserGists(GistAPI.USER).execute();
            Iterator<JsonElement> it = listUserGists.body().iterator();
            while (it.hasNext()) {
                JsonObject gistObject = it.next().getAsJsonObject();
                JsonObject files = gistObject.get("files").getAsJsonObject();

                if(id.equals(gistObject.get("id").getAsString())) {
                    Assert.assertEquals(createRequest.getDescription(), gistObject.get("description").getAsString());

                    for(String fileName : createRequest.getFiles().keySet()) {
                        Assert.assertTrue(files.has(fileName));
                    }

                    gistValidated = true;
                    break;
                }

            }
            Assert.assertTrue(gistValidated);
        }
    }
}
