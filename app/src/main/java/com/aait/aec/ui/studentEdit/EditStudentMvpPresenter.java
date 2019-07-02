package com.aait.aec.ui.studentEdit;

import com.aait.aec.data.db.model.Student;
import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface EditStudentMvpPresenter<V extends EditStudentMvpView> extends MvpPresenter<V> {

    void onUpdateClicked(Student student);

    void onCancelClicked();

    void onDeleteClicked(Student student);

    void onAddClicked(Student student);

}
