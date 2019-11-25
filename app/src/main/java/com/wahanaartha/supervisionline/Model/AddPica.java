package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPica {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ca")
    @Expose
    private String ca;
    @SerializedName("status_pica")
    @Expose
    private String statusPica;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("deadline")
    @Expose
    private String deadline;
    @SerializedName("new_deadline")
    @Expose
    private String newDeadline;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modi_by")
    @Expose
    private String modiBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getStatusPica() {
        return statusPica;
    }

    public void setStatusPica(String statusPica) {
        this.statusPica = statusPica;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModiBy() {
        return modiBy;
    }

    public void setModiBy(String modiBy) {
        this.modiBy = modiBy;
    }
}
