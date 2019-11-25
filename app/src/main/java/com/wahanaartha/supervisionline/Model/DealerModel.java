package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerModel {

    @SerializedName("no_dlr")
    @Expose
    private String noDlr;
    @SerializedName("nm_dlr")
    @Expose
    private String nmDlr;
    @SerializedName("type_dlr")
    @Expose
    private String typeDlr;

    public String getNoDlr() {
        return noDlr;
    }

    public void setNoDlr(String noDlr) {
        this.noDlr = noDlr;
    }

    public String getNmDlr() {
        return nmDlr;
    }

    public void setNmDlr(String nmDlr) {
        this.nmDlr = nmDlr;
    }

    public String getTypeDlr() {
        return typeDlr;
    }

    public void setTypeDlr(String typeDlr) {
        this.typeDlr = typeDlr;
    }

    @Override
    public String toString(){
        return nmDlr;
    }

}
