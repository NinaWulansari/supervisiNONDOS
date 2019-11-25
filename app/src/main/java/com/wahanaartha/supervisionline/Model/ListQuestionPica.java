package com.wahanaartha.supervisionline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListQuestionPica {
    @SerializedName("question")
    @Expose
    private List<QuestionPica> question = null;

    public List<QuestionPica> getQuestion() {
        return question;
    }

    public void setQuestion(List<QuestionPica> question) {
        this.question = question;
    }
}
