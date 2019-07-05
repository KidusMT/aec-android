package com.aait.aec.data.network.model.correct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Correct {

    @SerializedName("conainer_name")
    @Expose
    private String conainerName;
    @SerializedName("exam_id")
    @Expose
    private String examId;

    public String getConainerName() {
        return conainerName;
    }

    public void setConainerName(String conainerName) {
        this.conainerName = conainerName;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

}
