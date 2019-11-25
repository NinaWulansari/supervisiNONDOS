package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailPica {
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("child")
    @Expose
    private List<ChildPica> child = null;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

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

    public List<ChildPica> getChild() {
        return child;
    }

    public void setChild(List<ChildPica> child) {
        this.child = child;
    }

}
