package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListHistory {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_question")
    @Expose
    private String id_question;
    @SerializedName("id_answer")
    @Expose
    private String id_answer;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("last_root")
    @Expose
    private String lastRoot;
    @SerializedName("exist_good")
    @Expose
    private String existGood;
    @SerializedName("exist_not_good")
    @Expose
    private String existNotGood;
    @SerializedName("not_exist")
    @Expose
    private String notExist;
    @SerializedName("n_a")
    @Expose
    private String nA;
    @SerializedName("pi")
    @Expose
    private String pi;
    @SerializedName("ca")
    @Expose
    private String ca;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("new_deadline")
    @Expose
    private String newDeadline;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("deadline")
    @Expose
    private String deadline;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastRoot() {
        return lastRoot;
    }

    public void setLastRoot(String lastRoot) {
        this.lastRoot = lastRoot;
    }

    public String getExistGood() {
        return existGood;
    }

    public void setExistGood(String existGood) {
        this.existGood = existGood;
    }

    public String getExistNotGood() {
        return existNotGood;
    }

    public void setExistNotGood(String existNotGood) {
        this.existNotGood = existNotGood;
    }

    public String getNotExist() {
        return notExist;
    }

    public void setNotExist(String notExist) {
        this.notExist = notExist;
    }

    public String getNA() {
        return nA;
    }

    public void setNA(String nA) {
        this.nA = nA;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getId_question() {
        return id_question;
    }

    public void setId_question(String id_question) {
        this.id_question = id_question;
    }
}
