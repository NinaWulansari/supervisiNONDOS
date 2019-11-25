package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerLocal {
    @SerializedName("no_dlr")
    @Expose
    private String noDlr;
    @SerializedName("nm_dlr")
    @Expose
    private String nmDlr;

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

    @Override
    public String toString() {
        return nmDlr;
    }
}
