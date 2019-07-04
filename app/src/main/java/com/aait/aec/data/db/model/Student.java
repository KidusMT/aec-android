package com.aait.aec.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

@Entity(nameInDb = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long id;

    @SerializedName("name")
    @Property(nameInDb = "name")
    @Expose
    private String name;

    @SerializedName("result")
    @Property(nameInDb = "score")
    @Expose
    private String score;

    @SerializedName("ID")
    @Property(nameInDb = "stdId")
    @Expose
    private String stdId;


    public Student(String name, String score, String stdId) {
        this.name = name;
        this.score = score;
        this.stdId = stdId;
    }

    public Student(String name, String stdId) {
        this.name = name;
        this.stdId = stdId;
    }

    @Generated(hash = 672288817)
    public Student(Long id, String name, String score, String stdId) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.stdId = stdId;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }
}
