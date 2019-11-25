package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSupervisi {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("no_dlr")
    @Expose
    private String noDlr;
    @SerializedName("tgl_supervisi")
    @Expose
    private String tglSupervisi;
    @SerializedName("status_pica")
    @Expose
    private String statusPica;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("pi")
    @Expose
    private String pi;
    @SerializedName("modi_by")
    @Expose
    private String modiBy;
    @SerializedName("modified")
    @Expose
    private String modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoDlr() {
        return noDlr;
    }

    public void setNoDlr(String noDlr) {
        this.noDlr = noDlr;
    }

    public String getTglSupervisi() {
        return tglSupervisi;
    }

    public void setTglSupervisi(String tglSupervisi) {
        this.tglSupervisi = tglSupervisi;
    }

    public String getStatusPica() {
        return statusPica;
    }

    public void setStatusPica(String statusPica) {
        this.statusPica = statusPica;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getModiBy() {
        return modiBy;
    }

    public void setModiBy(String modiBy) {
        this.modiBy = modiBy;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
