package com.wahanaartha.supervisionline.Model;

public class Model {
    public static final int DONE = 0;
    public static final int NOT_DONE = 1;

    public int type;
    public String text;
    public int data;

    public Model(int type, String text, int data) {
        this.type = type;
        this.data = data;
        this.text = text;
    }


}
