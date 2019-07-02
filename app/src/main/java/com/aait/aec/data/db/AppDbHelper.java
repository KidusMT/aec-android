package com.aait.aec.data.db;

import com.aait.aec.data.db.model.DaoMaster;
import com.aait.aec.data.db.model.DaoSession;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.db.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Long> insertUser(final User user) {
        return Observable.fromCallable(() -> mDaoSession.getUserDao().insert(user));
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(() -> mDaoSession.getUserDao().loadAll());
    }

    @Override
    public Observable<List<Student>> getStudents() {
        return Observable.fromCallable(() -> mDaoSession.getStudentDao().loadAll());
    }

    @Override
    public Observable<Boolean> insertStudents(List<Student> students) {
        mDaoSession.getStudentDao().insertOrReplaceInTx(students);
        return Observable.fromCallable(() -> true);
    }

    @Override
    public Observable<Boolean> insertStudent(Student student) {
        mDaoSession.getStudentDao().insertOrReplace(student);
        return Observable.fromCallable(() -> true);
    }

    @Override
    public Observable<Boolean> deleteStudent(Student student) {
        mDaoSession.getStudentDao().deleteByKey(student.getId());
        return Observable.fromCallable(() -> true);
    }

    @Override
    public Observable<Boolean> updateStudent(Student student) {
        mDaoSession.getStudentDao().update(student);
        return Observable.fromCallable(() -> true);
    }

    @Override
    public Observable<Boolean> deleteAllStudents() {
        mDaoSession.getStudentDao().deleteAll();
        return Observable.fromCallable(() -> true);
    }

}
