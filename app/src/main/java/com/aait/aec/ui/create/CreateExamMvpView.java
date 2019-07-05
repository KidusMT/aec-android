package com.aait.aec.ui.create;

import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.network.model.Course;
import com.aait.aec.ui.base.MvpView;

import java.util.List;

public interface CreateExamMvpView extends MvpView {

    void updateExam(List<Student> students);

    void getCourses(List<Course> courses);
}
