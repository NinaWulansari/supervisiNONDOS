package com.wahanaartha.supervisionline.Model;

public class Valid {
    private Integer id;
    private String main_item;

    public Valid() {
    }

    public Valid(Integer id, String main_item) {
        this.id = id;
        this.main_item = main_item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainItem(String main_item) {
        return main_item;
    }

    public void setMainItem(String main_item) {
        this.main_item = main_item;
    }

}
