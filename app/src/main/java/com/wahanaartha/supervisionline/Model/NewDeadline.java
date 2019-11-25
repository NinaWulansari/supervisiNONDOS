package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewDeadline {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama_dlr")
    @Expose
    private Object namaDlr;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pi")
    @Expose
    private String pi;
    @SerializedName("ca")
    @Expose
    private String ca;
    @SerializedName("new_deadline")
    @Expose
    private String newDeadline;
    @SerializedName("status_pica")
    @Expose
    private String statusPica;
    @SerializedName("status_approve")
    @Expose
    private String statusApprove;
    @SerializedName("create_by")
    @Expose
    private String createBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getNamaDlr() {
        return namaDlr;
    }

    public void setNamaDlr(Object namaDlr) {
        this.namaDlr = namaDlr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getNewDeadline() {
        return newDeadline;
    }

    public void setNewDeadline(String newDeadline) {
        this.newDeadline = newDeadline;
    }

    public String getStatusPica() {
        return statusPica;
    }

    public void setStatusPica(String statusPica) {
        this.statusPica = statusPica;
    }

    public String getStatusApprove() {
        return statusApprove;
    }

    public void setStatusApprove(String statusApprove) {
        this.statusApprove = statusApprove;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

}
