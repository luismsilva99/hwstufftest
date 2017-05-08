package com.hw.luis.github.gist.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;


public class CreateRequest {

    @SerializedName("description")
    private String description;
    @SerializedName("public")
    private boolean publicc;
    @SerializedName("files")
    private HashMap<String, File> files;

    public CreateRequest(String description, boolean publicc, HashMap<String, File> files) {
        this.description = description;
        this.publicc = publicc;
        this.files = files;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublicc() {
        return publicc;
    }

    public void setPublicc(boolean publicc) {
        this.publicc = publicc;
    }

    public HashMap<String, File> getFiles() {
        return files;
    }

    public void setFiles(HashMap<String, File> files) {
        this.files = files;
    }
}
