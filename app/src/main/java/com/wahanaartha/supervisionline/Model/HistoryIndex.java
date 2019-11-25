package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryIndex {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama_dlr")
    @Expose
    private String namaDlr;
    @SerializedName("no_dlr")
    @Expose
    private String noDlr;
    @SerializedName("tgl_supervisi")
    @Expose
    private String tglSupervisi;
    @SerializedName("status_supervisi")
    @Expose
    private String statusSupervisi;
    @SerializedName("status_pica")
    @Expose
    private String statusPica;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status_approve")
    @Expose
    private Object statusApprove;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaDlr() {
        return namaDlr;
    }

    public void setNamaDlr(String namaDlr) {
        this.namaDlr = namaDlr;
    }

    public String getTglSupervisi() {
        return tglSupervisi;
    }

    public void setTglSupervisi(String tglSupervisi) {
        this.tglSupervisi = tglSupervisi;
    }

    public String getStatusSupervisi() {
        return statusSupervisi;
    }

    public void setStatusSupervisi(String statusSupervisi) {
        this.statusSupervisi = statusSupervisi;
    }

    public String getStatusPica() {
        return statusPica;
    }

    public void setStatusPica(String statusPica) {
        this.statusPica = statusPica;
    }

    public Object getStatusApprove() {
        return statusApprove;
    }

    public void setStatusApprove(Object statusApprove) {
        this.statusApprove = statusApprove;
    }

    public String getNoDlr() {
        return noDlr;
    }

    public void setNoDlr(String noDlr) {
        this.noDlr = noDlr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}