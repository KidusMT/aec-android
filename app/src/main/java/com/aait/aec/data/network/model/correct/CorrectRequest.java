package com.aait.aec.data.network.model.correct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CorrectRequest {

    @SerializedName("container_name")
    @Expose
    private String containerName;
    @SerializedName("exam_id")
    @Expose
    private String examId;

    public String getConainerName() {
        return containerName;
    }

    public void setConainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public CorrectRequest(String containerName, String examId) {
        this.containerName = containerName;
        this.examId = examId;
    }
}
