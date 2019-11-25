package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notif {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama_dlr")
    @Expose
    private String namaDlr;
    @SerializedName("main_item")
    @Expose
    private String mainItem;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pi")
    @Expose
    private String pi;
    @SerializedName("ca")
    @Expose
    private String ca;
    @SerializedName("deadline")
    @Expose
    private String deadline;
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

    public String getNamaDlr() {
        return namaDlr;
    }

    public void setNamaDlr(String namaDlr) {
        this.namaDlr = namaDlr;
    }

    public String getMainItem() {
        return mainItem;
    }

    public void setMainItem(String mainItem) {
        this.mainItem = mainItem;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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
