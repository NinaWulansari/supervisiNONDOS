package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerPica {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("title_header")
    @Expose
    private String titleHeader;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("pi")
    @Expose
    private String pi;
    @SerializedName("ca")
    @Expose
    private Object ca;
    @SerializedName("status_approve")
    @Expose
    private Object statusApprove;
    @SerializedName("status_pica")
    @Expose
    private String statusPica;
    @SerializedName("status_supervisi")
    @Expose
    private String statusSupervisi;
    @SerializedName("pic")
    @Expose
    private Object pic;
    @SerializedName("deadline")
    @Expose
    private Object deadline;
    @SerializedName("new_deadline")
    @Expose
    private Object new_deadline;
    @SerializedName("reason")
    @Expose
    private Object reason;
    @SerializedName("modi_by")
    @Expose
    private Object modiBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTitleHeader() {
        return titleHeader;
    }

    public void setTitleHeader(String titleHeader) {
        this.titleHeader = titleHeader;
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

    public Object getStatusApprove() {
        return statusApprove;
    }

    public void setStatusApprove(Object statusApprove) {
        this.statusApprove = statusApprove;
    }

    public String getStatusPica() {
        return statusPica;
    }

    public void setStatusPica(String statusPica) {
        this.statusPica = statusPica;
    }

    public String getStatusSupervisi() {
        return statusSupervisi;
    }

    public void setStatusSupervisi(String statusSupervisi) {
        this.statusSupervisi = statusSupervisi;
    }

    public Object getPic() {
        return pic;
    }

    public void setPic(Object pic) {
        this.pic = pic;
    }

    public void setCa(Object ca) {
        this.ca = ca;
    }

    public Object getDeadline() {
        return deadline;
    }

    public void setDeadline(Object deadline) {
        this.deadline = deadline;
    }

    public Object getNew_deadline() {
        return new_deadline;
    }

    public void setNew_deadline(Object new_deadline) {
        this.new_deadline = new_deadline;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }

    public Object getModiBy() {
        return modiBy;
    }

    public void setModiBy(Object modiBy) {
        this.modiBy = modiBy;
    }
}
