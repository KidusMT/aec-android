package com.aait.aec.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "student")
public class Student {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "score")
    private String score;

    @Property(nameInDb = "stdId")
    private String stdId;


    public Student(String name, String score, String stdId) {
        this.name = name;
        this.score = score;
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