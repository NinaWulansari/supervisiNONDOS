package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("last_root")
    @Expose
    private String lastRoot;
    @SerializedName("exist_good")
    @Expose
    private String existGood;
    @SerializedName("exist_not_good")
    @Expose
    private String existNotGood;
    @SerializedName("not_exist")
    @Expose
    private String notExist;
    @SerializedName("n_a")
    @Expose
    private String nA;
    private String soal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastRoot() {
        return lastRoot;
    }

    public void setLastRoot(String lastRoot) {
        this.lastRoot = lastRoot;
    }

    public String getExistGood() {
        return existGood;
    }

    public void setExistGood(String existGood) {
        this.existGood = existGood;
    }

    public String getExistNotGood() {
        return existNotGood;
    }

    public void setExistNotGood(String existNotGood) {
        this.existNotGood = existNotGood;
    }

    public String getNotExist() {
        return notExist;
    }

    public void setNotExist(String notExist) {
        this.notExist = notExist;
    }

    public String getNA() {
        return nA;
    }

    public void setNA(String nA) {
        this.nA = nA;
    }

    @Override
    public String toString() {
        return soal;
    }


}
