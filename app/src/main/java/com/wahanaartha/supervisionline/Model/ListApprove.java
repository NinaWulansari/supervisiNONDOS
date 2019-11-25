package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListApprove {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id_sa")
    @Expose
    private String idSa;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("no_dlr")
    @Expose
    private String noDlr;
    @SerializedName("tgl_supervisi")
    @Expose
    private String tglSupervisi;
    @SerializedName("main_item")
    @Expose
    private String mainItem;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("pic")
    @Expose
    private Object pic;
    @SerializedName("pi")
    @Expose
    private String pi;
    @SerializedName("ca")
    @Expose
    private String ca;
    @SerializedName("duration")
    @Expose
    private Object duration;
    @SerializedName("deadline")
    @Expose
    private String deadline;
    @SerializedName("new_deadline")
    @Expose
    private String newDeadline;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modi_by")
    @Expose
    private Object modiBy;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("create_by")
    @Expose
    private String createBy;
    @SerializedName("status_supervisi")
    @Expose
    private String statusSupervisi;
    @SerializedName("status_pica")
    @Expose
    private String statusPica;
    @SerializedName("status_approve")
    @Expose
    private Object statusApprove;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("nm_dlr")
    @Expose
    private String nmDlr;
    @SerializedName("total")
    @Expose
    private String total;

    public boolean isSelectAll;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTglSupervisi() {
        return tglSupervisi;
    }

    public void setTglSupervisi(String tglSupervisi) {
        this.tglSupervisi = tglSupervisi;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getPic() {
        return pic;
    }

    public void setPic(Object pic) {
        this.pic = pic;
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

    public Object getDuration() {
        return duration;
    }

    public void setDuration(Object duration) {
        this.duration = duration;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getNewDeadline() {
        return newDeadline;
    }

    public void setNewDeadline(String newDeadline) {
        this.newDeadline = newDeadline;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Object getModiBy() {
        return modiBy;
    }

    public void setModiBy(Object modiBy) {
        this.modiBy = modiBy;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNmDlr() {
        return nmDlr;
    }

    public void setNmDlr(String nmDlr) {
        this.nmDlr = nmDlr;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIdSa() {
        return idSa;
    }

    public void setIdSa(String idSa) {
        this.idSa = idSa;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }


    public boolean getSelectAll() {
        return isSelectAll;
    }

    public void setSelectAll(boolean val) {
        this.isSelectAll = val;
    }

}
