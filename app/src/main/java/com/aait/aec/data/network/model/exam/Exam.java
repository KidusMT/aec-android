package com.aait.aec.data.network.model.exam;

import com.aait.aec.data.db.model.Student;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Exam {

    @SerializedName("exam_name")
    @Expose
    private String examName;
    @SerializedName("exam_type")
    @Expose
    private String examType;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("exam_weight")
    @Expose
    private Integer examWeight;
    @SerializedName("answers")
    @Expose
    private List<Answers> answers;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("students")
    @Expose
    private List<Student> students = null;

    public Exam(String examName, String examType, Integer examWeight, List<Answers> answers, String userId, List<Student> students) {
        this.examName = examName;
        this.examType = examType;
        this.examWeight = examWeight;
        this.answers = answers;
        this.userId = userId;
        this.students = students;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getExamWeight() {
        return examWeight;
    }

    public void setExamWeight(Integer examWeight) {
        this.examWeight = examWeight;
    }

    public List<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answers> answers) {
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
