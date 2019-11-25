package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertSupervisi {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pi")
    @Expose
    private String pi;
    @SerializedName("main_item")
    @Expose
    private String mainItem;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("no_dlr")
    @Expose
    private String noDlr;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("create_by")
    @Expose
    private String createBy;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status_supervisi")
    @Expose
    private String statusSupervisi;
    @SerializedName("status_pica")
    @Expose
    private String statusPica;
    @SerializedName("status_approve")
    @Expose
    private String statusApprove;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("tgl_supervisi")
    @Expose
    private String tglSupervisi;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("nama_dlr")
    @Expose
    private String namaDlr;
    @SerializedName("pic")
    @Expose
    private String pic;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getMainItem() {
        return mainItem;
    }

    public void setMainItem(String mainItem) {
        this.mainItem = mainItem;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNoDlr() {
        return noDlr;
    }

    public void setNoDlr(String noDlr) {
        this.noDlr = noDlr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getStatusApprove() {
        return statusApprove;
    }

    public void setStatusApprove(String statusApprove) {
        this.statusApprove = statusApprove;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTglSupervisi() {
        return tglSupervisi;
    }

    public void setTglSupervisi(String tglSupervisi) {
        this.tglSupervisi = tglSupervisi;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNamaDlr() {
        return namaDlr;
    }

    public void setNamaDlr(String namaDlr) {
        this.namaDlr = namaDlr;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
