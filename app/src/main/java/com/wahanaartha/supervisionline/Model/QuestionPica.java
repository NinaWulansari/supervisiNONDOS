package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ratri on 10/23/2018.
 */

public class QuestionPica {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("kategori")
    @Expose
    private String kategori;
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
    private String ca;
    @SerializedName("status_approve")
    @Expose
    private String statusApprove;
    @SerializedName("status_pica")
    @Expose
    private String statusPica;
    @SerializedName("status_supervisi")
    @Expose
    private String statusSupervisi;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("detail")
    @Expose
    private List<Detail> detail = null;

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

    public String getCa() {
        return ca;
    }

    public void setPa(String ca) {
        this.ca = ca;
    }

    public String getStatusApprove() {
        return statusApprove;
    }

    public void setStatusApprove(String statusApprove) {
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }
}
