package com.aait.aec.ui.create;


import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.network.model.exam.Answers;
import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

import java.util.List;

@PerActivity
public interface CreateExamMvpPresenter<V extends CreateExamMvpView> extends MvpPresenter<V> {

    void createExam(String examName, String examType, int examWeight, List<Answers> answers, List<Student> students);

    void loadCourse();

    void loadStudents();
}
