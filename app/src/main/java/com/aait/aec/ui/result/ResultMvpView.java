package com.aait.aec.ui.result;

import com.aait.aec.data.db.model.Student;
import com.aait.aec.ui.base.MvpView;

import java.util.List;

public interface ResultMvpView extends MvpView {
    void showStudents(List<Student> students);
}
