package com.wahanaartha.supervisionline.Model;

public class Remark {

    int id;
    int urut;
    int parent_id;
    String parent;
    String title;
    String description;
    int headline;
    int listdown;

    public Remark(int id, String parent, String title) {
        this.id = id;
        this.parent = parent;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUrut() {
        return urut;
    }

    public void setUrut(int urut) {
        this.urut = urut;
    }

    public int getParentId() {
        return parent_id;
    }

    public void setParentId(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHeadline() {
        return headline;
    }

    public void setHeadline(int headline) {
        this.headline = headline;
    }

    public int getListdown() {
        return listdown;
    }

    public void setListdown(int listdown) {
        this.listdown = listdown;
    }
}
